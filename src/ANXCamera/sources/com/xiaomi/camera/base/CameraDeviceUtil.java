package com.xiaomi.camera.base;

import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraCharacteristics.Key;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureResult;
import android.os.Parcelable;
import android.util.Log;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.lang.reflect.Method;

public class CameraDeviceUtil {
    private static final String TAG = CameraDeviceUtil.class.getSimpleName();

    private CameraDeviceUtil() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0057 A:{ExcHandler: java.lang.ClassNotFoundException (r7_1 'e' java.lang.Throwable), Splitter: B:10:0x0025} */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0057 A:{ExcHandler: java.lang.ClassNotFoundException (r7_1 'e' java.lang.Throwable), Splitter: B:10:0x0025} */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0057 A:{ExcHandler: java.lang.ClassNotFoundException (r7_1 'e' java.lang.Throwable), Splitter: B:10:0x0025} */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0057 A:{ExcHandler: java.lang.ClassNotFoundException (r7_1 'e' java.lang.Throwable), Splitter: B:10:0x0025} */
    /* JADX WARNING: Missing block: B:15:0x0057, code:
            r7 = move-exception;
     */
    /* JADX WARNING: Missing block: B:16:0x0058, code:
            android.util.Log.e(TAG, "prepareCalibrationDataForAlgo: call reflect method failed!", r7);
     */
    /* JADX WARNING: Missing block: B:17:0x0066, code:
            throw new java.lang.RuntimeException("getCameraCharacteristics's dualCalibrationData failed");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void prepareCalibrationDataForAlgo(Context context, String str) {
        try {
            CameraCharacteristics cameraCharacteristics = ((CameraManager) context.getSystemService("camera")).getCameraCharacteristics(str);
            Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
            if (num != null) {
                boolean z = num.intValue() == 0;
                try {
                    byte[] bArr = (byte[]) cameraCharacteristics.get((Key) Class.forName("android.hardware.camera2.CameraCharacteristics$Key").getDeclaredConstructor(new Class[]{String.class, Class.class}).newInstance(new Object[]{"com.xiaomi.camera.algoup.dualCalibrationData", byte[].class}));
                    if (bArr != null) {
                        CommonUtil.saveCameraCalibrationToFile(context, bArr, z);
                    }
                } catch (Throwable e) {
                }
            }
        } catch (Throwable e2) {
            Log.e(TAG, "prepareCalibrationDataForAlgo: get getCameraCharacteristics failed!", e2);
        }
    }

    public static ICustomCaptureResult getCustomCaptureResult(CaptureResult captureResult) {
        try {
            Method method = captureResult.getClass().getMethod("getNativeCopy", new Class[0]);
            method.setAccessible(true);
            Parcelable parcelable = (Parcelable) method.invoke(captureResult, new Object[0]);
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getCustomCaptureResult: cameraMetadataNative =");
            stringBuilder.append(parcelable);
            Log.d(str, stringBuilder.toString());
            ICustomCaptureResult iCustomCaptureResult = new ICustomCaptureResult();
            iCustomCaptureResult.setFrameNumber(captureResult.getFrameNumber());
            iCustomCaptureResult.setRequest(captureResult.getRequest());
            iCustomCaptureResult.setSequenceId(captureResult.getSequenceId());
            iCustomCaptureResult.setResults(parcelable);
            Long l = (Long) captureResult.get(CaptureResult.SENSOR_TIMESTAMP);
            if (l != null) {
                iCustomCaptureResult.setTimeStamp(l.longValue());
            }
            String str2 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("getCustomCaptureResult: ");
            stringBuilder2.append(iCustomCaptureResult);
            Log.d(str2, stringBuilder2.toString());
            return iCustomCaptureResult;
        } catch (Throwable e) {
            Log.e(TAG, "getCustomCaptureResult: getCustomCaptureResult", e);
            return null;
        }
    }
}
