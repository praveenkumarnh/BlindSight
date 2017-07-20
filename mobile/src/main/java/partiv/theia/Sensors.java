package partiv.theia;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class Sensors implements SensorEventListener
{

    private SensorManager mSensorManager;
    private final Context context;

    private float[] gravity = new float[3];
    private float[] magnetic = new float[3];
    private float[] linearAcc = new float[3];

    private double azimuth, pitch, roll;

    Sensors(Context context)
    {
        this.context = context;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            for(int i = 0; i < 3; i++)
                gravity[i] = event.values[i];
        }
        else if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
            for(int i = 0; i < 3; i++)
                magnetic[i] = event.values[i];
        }
        else if(event.sensor.getType()== Sensor.TYPE_LINEAR_ACCELERATION)
        {
            for(int i = 0; i < 3; i++)
                linearAcc[i] = event.values[i];
        }

        float[] R = new float[9];
        float[] I = new float[9];
        float[] orientation = new float[3];

        if (gravity != null && magnetic != null) {
            if (SensorManager.getRotationMatrix(R, I, gravity, magnetic)) {
                SensorManager.getOrientation(R, orientation);
                azimuth = Math.toDegrees(orientation[0]);
                pitch = Math.toDegrees(orientation[1]);
                roll = Math.toDegrees(orientation[2]);
                //Log.d("Orientation", Double.toString(azimuth) + " " + Double.toString(pitch) + " " + Double.toString(roll));
            }
        }
        //Log.d("Accerometer", Float.toString(x) + " " + Float.toString(y) + " " + Float.toString(z));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
