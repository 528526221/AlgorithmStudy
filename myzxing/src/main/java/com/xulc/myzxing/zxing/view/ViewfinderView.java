package com.xulc.myzxing.zxing.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.xulc.myzxing.R;
import com.xulc.myzxing.zxing.DensityUtil;
import com.xulc.myzxing.zxing.camera.CameraManager;

import java.util.Collection;
import java.util.HashSet;


/**
 * Date：2018/3/20
 * Desc：二维码扫描美化界面View
 * Created by xuliangchun.
 */
public final class ViewfinderView extends View {
	/**
	 * 刷新界面的时间
	 */
	private static final long ANIMATION_DELAY = 10L;
	private static final int OPAQUE = 0xFF;

	/**
	 * 四个绿色边角对应的长度
	 */
	private int cornerLength = 44;

	/**
	 * 四个绿色边角对应的宽度
	 */
	private int CORNER_WIDTH = 10;

	/**
	 * 中间那条线每次刷新移动的距离
	 */
	private int SPEEN_DISTANCE = 5;

	/**
	 * 字体大小
	 */
	private int tipTextSize = 30;

	/**
	 * 文本与扫描框的上间距
	 */
	private int tipMarginTop = 50;

	/**
	 * 画笔对象的引用
	 */
	private Paint paint;

	/**
	 * 中间滑动线的最顶端位置
	 */
	private int slideTop;

	/**
	 * 中间滑动线的高度
	 */
	private int slideLineHeight = 3;

	/**
	 * 扫描线的渐变
	 */
	private LinearGradient lineLinearGradient;

	/**
	 * 扫描线距离扫描区域top这一块的渐变
	 */
	private LinearGradient scanLinearGradient;


	/**
	 * 将扫描的二维码拍下来，这里没有这个功能，暂时不考虑
	 */
	private Bitmap resultBitmap;
	private final int maskColor;
	private final int resultColor;

	private final int resultPointColor;
	private Collection<ResultPoint> possibleResultPoints;
	private Collection<ResultPoint> lastPossibleResultPoints;

	boolean isFirst = true;
	//扫描区域渐变色
	private int[] scanRegionColors = new int[]{Color.TRANSPARENT, Color.parseColor("#48FFFFFF")};

	private Bitmap flashLightOnBitmap;
	private Bitmap flashLightOffBitmap;
	private int flashLightMarginBottom = 55;

	private int lightTipMarginBottom = 22;
	private int lightTextSize = 24;
	//闪光灯打开/关闭状态
	private boolean isFlightOpen = false;
	//光感感应到黑暗
	private boolean isSensationDarkLight = false;

	private Rect touchRectF;//保存扫描区域作判断依据

	private boolean isNotFoundData = false;//未发现二维码/条形码

	private ViewFinderListener listener;

	public interface ViewFinderListener{
		void onRestartScan();
	}


	public ViewfinderView(Context context, AttributeSet attrs) {
		super(context, attrs);

		if (!isInEditMode()){
			cornerLength = DensityUtil.getInstance(getContext()).getRateHeight(44);
			tipTextSize = DensityUtil.getInstance(getContext()).getRateHeight(30);
			tipMarginTop = DensityUtil.getInstance(getContext()).getRateHeight(50);
			slideLineHeight = DensityUtil.getInstance(getContext()).getRateHeight(3);
			flashLightMarginBottom = DensityUtil.getInstance(getContext()).getRateHeight(55);
			lightTipMarginBottom = DensityUtil.getInstance(getContext()).getRateHeight(22);
			lightTextSize = DensityUtil.getInstance(getContext()).getRateHeight(24);
		}

		paint = new Paint();
		paint.setAntiAlias(true);

		maskColor = Color.parseColor("#88000000");
		resultColor = Color.parseColor("#b0000000");
		resultPointColor = Color.parseColor("#c0ffff00");
		possibleResultPoints = new HashSet<>(5);
		flashLightOnBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_light_on);
		flashLightOffBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_light_off);

	}

	@Override
	public void onDraw(Canvas canvas) {
		//从CameraManager读取扫描的区域
		if (isInEditMode()){
			return;
		}
		Rect frame = CameraManager.get().getFramingRect();
		touchRectF = frame;
		if (frame == null) {
			//设计要求 摄像头无图像时应该要把外框绘制出来
//			return;
			int width = DensityUtil.getInstance(getContext()).getScreenW() * 3 / 5;
			if (width < 450) {
				width = 450;
			} else if (width > 720) {
				width = 720;
			}
			int height = width;
			int leftOffset = (DensityUtil.getInstance(getContext()).getScreenW() - width) / 2;
			int topOffset = leftOffset*23/15;
			frame = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
			drawFrameWithNoCamera(canvas,frame);
			drawFrameCorner(canvas, frame);//绘制扫描框4角
			slideTop = frame.centerY();
			drawScanLine(canvas,frame);
			drawScanRegion(canvas,frame);

			drawText(canvas, frame, "摄像头未开启");// 画扫描框下面的字


			Log.i("xlc","无法读取摄像头区域");
		}else {
			//初始化中间线滑动的最上边和最下边
			if(isFirst){
				isFirst = false;
				slideTop = frame.top;
			}

			// 绘制扫描框以外4个区域
			drawMask(canvas, frame);

			// 如果有二维码结果的Bitmap，在扫取景框内绘制不透明的result Bitmap
			if (resultBitmap != null) {
				// Draw the opaque result bitmap over the scanning rectangle
				paint.setAlpha(OPAQUE);
				canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
			} else {
				drawText(canvas, frame, "对准二维码/条形码至框内扫描");// 画扫描框下面的字
				drawScanLine(canvas, frame);//绘制扫描线
				drawScanRegion(canvas,frame);//绘制扫描线扫过的区域
				drawResultPoint(canvas, frame);//绘制扫描点标记
				if (isNotFoundData){
					drawTextNotFound(canvas,frame);//绘制未发现二维码/条形码  在这部分代码中会绘制边框4角
				}else {
					drawFrameCorner(canvas, frame);//绘制扫描框4角
				}

				drawFlashLight(canvas,frame);//绘制手电


				//只刷新扫描框的内容，其他地方不刷新

				postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
						frame.right, frame.bottom);
			}
		}

	}



	int downX = 0 ;
	int downY = 0 ;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				downX = (int) event.getX();
				downY = (int) event.getY();
				break;
			case MotionEvent.ACTION_UP:
				//没有扫描到数据时 点击会继续扫描
				if (isNotFoundData){
					restartScan();
				}else if (isFlightOpen || isSensationDarkLight){
					if (downX>touchRectF.left+touchRectF.width()/4 && downX<touchRectF.right-touchRectF.width()/4 && downY>touchRectF.bottom-touchRectF.height()/3 && downY<touchRectF.bottom){
						if (Math.abs(downX-event.getX())<20){
							if (isFlightOpen){
								Log.i("xlc","关闭闪光");
								if (CameraManager.get().setFlashLight(false)){
									isFlightOpen = false;
								}
							}else if (isSensationDarkLight){
								Log.i("xlc","打开闪光");
								if (CameraManager.get().setFlashLight(true)){
									isFlightOpen = true;
								}
							}
						}
					}
				}


				break;
		}
		return true;
	}



	/**
	 * 设置光感强度是否感受到黑暗了
	 * @param sensationDarkLight
	 */
	public void setSensationDarkLight(boolean sensationDarkLight) {
		isSensationDarkLight = sensationDarkLight;
	}

	/**
     * 设置未发现二维码/条形码数据
	 * @param notFoundData
	 */
	public void setNotFoundData(boolean notFoundData) {
		isNotFoundData = notFoundData;
	}

	/**
     * 设置回调
	 * @param listener
	 */
	public void setListener(ViewFinderListener listener) {
		this.listener = listener;
	}

	/**
	 * 开始新的扫描
	 */
	private void restartScan() {
		isNotFoundData = false;
		if (listener != null){
			listener.onRestartScan();
		}
	}

	/**
	 * 绘制未发现二维码/条形码
	 * 同时注意还要绘制外框线
	 * @param canvas
	 * @param frame
	 */
	private void drawTextNotFound(Canvas canvas, Rect frame) {

		//绘制外框线
		Path path = new Path();
		path.moveTo(frame.left,frame.top);
		path.lineTo(frame.right,frame.top);
		path.lineTo(frame.right,frame.bottom);
		path.lineTo(frame.left,frame.bottom);
		path.close();
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawPath(path,paint);
		paint.setStyle(Paint.Style.FILL);
		//绘制扫描框4角
		drawFrameCorner(canvas, frame);
		//绘制遮罩
		paint.setColor(Color.parseColor("#88000000"));
		canvas.drawRect(0,0,getWidth(),getHeight(),paint);
		paint.setColor(Color.WHITE);
		paint.setTextSize(tipTextSize);
		canvas.drawText("未发现二维码/条形码",(getWidth() - paint.measureText("未发现二维码/条形码")) / 2,frame.top+frame.height()/2,paint);
		paint.setColor(Color.parseColor("#9E9E9E"));
		paint.setTextSize(lightTextSize);
		canvas.drawText("轻触屏幕继续扫描",(getWidth() - paint.measureText("轻触屏幕继续扫描")) / 2,frame.top+frame.height()/2+cornerLength,paint);
	}


	/**
	 * 为了摄像头没有打开时绘制下背景 以及扫描框的线条
	 * @param canvas
	 * @param frame
	 */
	private void drawFrameWithNoCamera(Canvas canvas, Rect frame) {
		paint.setColor(Color.parseColor("#3a3a3a"));
		canvas.drawRect(0,0,getWidth(),getHeight(),paint);


		Path path = new Path();
		path.moveTo(frame.left,frame.top);
		path.lineTo(frame.right,frame.top);
		path.lineTo(frame.right,frame.bottom);
		path.lineTo(frame.left,frame.bottom);
		path.close();
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawPath(path,paint);

		paint.setStyle(Paint.Style.FILL);
	}


	/**
	 * 绘制手电
	 * @param canvas
	 * @param frame
	 */
	private void drawFlashLight(Canvas canvas, Rect frame) {
		if (isFlightOpen){
			paint.setTextSize(lightTextSize);
			canvas.drawBitmap(flashLightOnBitmap,frame.centerX()-flashLightOnBitmap.getWidth()/2,frame.bottom-flashLightOnBitmap.getHeight()-flashLightMarginBottom,paint);

			canvas.drawText("轻点关闭",(getWidth() - paint.measureText("轻点关闭")) / 2,frame.bottom-lightTipMarginBottom,paint);
		}else if (isSensationDarkLight){
				paint.setTextSize(lightTextSize);
				canvas.drawBitmap(flashLightOffBitmap,frame.centerX()-flashLightOffBitmap.getWidth()/2,frame.bottom-flashLightOffBitmap.getHeight()-flashLightMarginBottom,paint);

				canvas.drawText("轻点照亮",(getWidth() - paint.measureText("轻点照亮")) / 2,frame.bottom-lightTipMarginBottom,paint);
		}

	}

	/**
	 * 绘制扫描线扫过的区域
	 * @param canvas
	 * @param frame
	 */
	private void drawScanRegion(Canvas canvas, Rect frame) {
		scanLinearGradient = new LinearGradient(frame.left,0,frame.left,slideTop,scanRegionColors,null, Shader.TileMode.REPEAT);
		paint.setShader(scanLinearGradient);
		canvas.drawRect(frame.left,frame.top,frame.right,slideTop+slideLineHeight,paint);
		paint.setShader(null);

	}

	/**
	 * 绘制扫描结果的小点点
	 * @param canvas
	 * @param frame
	 */
	private void drawResultPoint(Canvas canvas, Rect frame) {
		Collection<ResultPoint> currentPossible = possibleResultPoints;
		Collection<ResultPoint> currentLast = lastPossibleResultPoints;
		if (currentPossible.isEmpty()) {
			lastPossibleResultPoints = null;
		} else {
			possibleResultPoints = new HashSet<ResultPoint>(5);
			lastPossibleResultPoints = currentPossible;
			paint.setAlpha(OPAQUE);
			paint.setColor(resultPointColor);
			for (ResultPoint point : currentPossible) {
				canvas.drawCircle(frame.left + point.getX(), frame.top
						+ point.getY(), 6.0f, paint);
			}
		}
		if (currentLast != null) {
			paint.setAlpha(OPAQUE / 2);
			paint.setColor(resultPointColor);
			for (ResultPoint point : currentLast) {
				canvas.drawCircle(frame.left + point.getX(), frame.top
						+ point.getY(), 3.0f, paint);
			}
		}
	}

	/**
	 * 绘制横线
	 * @param canvas
	 * @param frame
	 */
	private void drawScanLine(Canvas canvas, Rect frame) {
		if (lineLinearGradient == null){
			lineLinearGradient = new LinearGradient(frame.left,frame.top,frame.right,frame.top,new int[]{Color.parseColor("#32FFFFFF"), Color.WHITE, Color.parseColor("#32FFFFFF")},null, Shader.TileMode.REPEAT);
		}
		paint.setShader(lineLinearGradient);
		//绘制中间的线,每次刷新界面，中间的线往下移动SPEEN_DISTANCE
		slideTop += SPEEN_DISTANCE;
		if(slideTop >= frame.bottom){
			slideTop = frame.top;
		}
		canvas.drawRect(frame.left,slideTop,frame.right,slideTop+slideLineHeight,paint);
		paint.setShader(null);

	}

	/**
	 * 绘制提示文本
	 * @param canvas
	 * @param frame
	 */
	private void drawText(Canvas canvas, Rect frame, String drawText) {
		paint.setColor(Color.WHITE);
		paint.setTextSize(tipTextSize);
		canvas.drawText(drawText, (getWidth() - paint.measureText(drawText)) / 2, frame.bottom+tipMarginTop, paint);
	}


	/**
	 * 绘制四个角
	 * @param canvas
	 * @param frame
	 */
	private void drawFrameCorner(Canvas canvas, Rect frame) {
		//画扫描框边上的角，总共8个部分
		paint.setColor(Color.WHITE);
		canvas.drawRect(frame.left, frame.top, frame.left + cornerLength,
				frame.top + CORNER_WIDTH, paint);
		canvas.drawRect(frame.left, frame.top, frame.left + CORNER_WIDTH, frame.top
				+ cornerLength, paint);
		canvas.drawRect(frame.right - cornerLength, frame.top, frame.right,
				frame.top + CORNER_WIDTH, paint);
		canvas.drawRect(frame.right - CORNER_WIDTH, frame.top, frame.right, frame.top
				+ cornerLength, paint);
		canvas.drawRect(frame.left, frame.bottom - CORNER_WIDTH, frame.left
				+ cornerLength, frame.bottom, paint);
		canvas.drawRect(frame.left, frame.bottom - cornerLength,
				frame.left + CORNER_WIDTH, frame.bottom, paint);
		canvas.drawRect(frame.right - cornerLength, frame.bottom - CORNER_WIDTH,
				frame.right, frame.bottom, paint);
		canvas.drawRect(frame.right - CORNER_WIDTH, frame.bottom - cornerLength,
				frame.right, frame.bottom, paint);
	}

	/**
	 * 绘制扫描框外围半透明区域
	 * @param canvas
	 * @param frame
	 */
	private void drawMask(Canvas canvas, Rect frame) {
		//画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
		//扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
		paint.setColor(resultBitmap != null ? resultColor : maskColor);
		canvas.drawRect(0, 0, getWidth(), frame.top, paint);
		canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
		canvas.drawRect(frame.right + 1, frame.top, getWidth(), frame.bottom + 1,
				paint);
		canvas.drawRect(0, frame.bottom + 1, getWidth(), getHeight(), paint);
	}

	public void drawViewfinder() {
		resultBitmap = null;
		invalidate();
	}

	/**
	 * Draw a bitmap with the result points highlighted instead of the live
	 * scanning display.
	 * @param barcode
	 * An image of the decoded barcode.
	 */
	public void drawResultBitmap(Bitmap barcode) {
		resultBitmap = barcode;
		invalidate();
	}

	public void addPossibleResultPoint(ResultPoint point) {
		possibleResultPoints.add(point);
	}

}
