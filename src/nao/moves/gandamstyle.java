package nao.moves;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.ALMotion;

import components.json.JSONArray;
import nao.motors;

public class gandamstyle implements SendClassName {

    public Application application;

    @Override
    public void start(Application application, JSONArray args){
        this.application = application;

        try{
            resetMotion();

            float speed = 0.1f;

            ALMotion p = new ALMotion(application.session());
            p.setAngles(motors.LHipRoll.name, 0.4f, speed);
            p.setAngles(motors.LAnkleRoll.name, -0.4f, speed);

            p.setAngles(motors.RHipRoll.name, -0.4f, speed);
            p.setAngles(motors.RAnkleRoll.name, 0.4f, speed);

            p.setAngles(motors.LShoulderPitch.name, 1f, speed);
            p.setAngles(motors.RShoulderPitch.name, 1f, speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(2_000);

            p.setAngles(motors.LShoulderRoll.name, Math.toRadians(-18), speed);
            p.setAngles(motors.LShoulderPitch.name, Math.toRadians(50), speed);
            p.setAngles(motors.LElbowRoll.name, Math.toRadians(-40), speed);

            p.setAngles(motors.RShoulderRoll.name, Math.toRadians(18), speed);
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(40), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(40), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(2_000);

            speed = 0.7f;
            boolean up = false;
            int count = 0;
            int tritt = 0;
            while(count < 16) {
                if(up) {
                    p.setAngles(motors.LShoulderPitch.name, Math.toRadians(10), speed);
                    p.setAngles(motors.RShoulderPitch.name, Math.toRadians(00), speed);
                }else {
                    p.setAngles(motors.LShoulderPitch.name, Math.toRadians(50), speed);
                    p.setAngles(motors.RShoulderPitch.name, Math.toRadians(40), speed);
                }
                if(up) {
                    switch (tritt) {
                        case 1:
                        case 4:
                        case 6:
                        case 7:
                            linksSchritt(1);
                            tritt++;
                            break;
                        case 0:
                        case 2:
                        case 3:
                        case 5:
                            rechtsSchritt(1);
                            tritt++;
                            break;
                        default:
                            break;
                    }
                }

                up = !up;
                count++;
                p.waitUntilMoveIsFinished();
                Thread.sleep(500);
            }

            speed = 0.2f;

            p.setAngles(motors.RElbowRoll.name, Math.toRadians(0), speed);
            p.setAngles(motors.RShoulderRoll.name, Math.toRadians(-90), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(75), speed);
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(-90), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(1_500);

            int durchlauf = 0;
            while(durchlauf < 8){
                switch (durchlauf) {
                    case 1:
                    case 4:
                    case 6:
                    case 7:
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(45), speed);
                        p.waitUntilMoveIsFinished();
                        Thread.sleep(0_700);
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(0), speed);
                        p.setAngles(motors.RElbowRoll.name, Math.toRadians(45), speed);
                        p.waitUntilMoveIsFinished();
                        Thread.sleep(0_700);
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(-45), speed);
                        p.setAngles(motors.RElbowRoll.name, Math.toRadians(90), speed);
                        p.waitUntilMoveIsFinished();
                        Thread.sleep(0_700);
                        linksSchritt(1);
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(0), speed);
                        p.setAngles(motors.RElbowRoll.name, Math.toRadians(135), speed);
                        p.setAngles(motors.RShoulderRoll.name, Math.toRadians(-45), speed);
                        p.waitUntilMoveIsFinished();
                        Thread.sleep(0_700);
                        p.setAngles(motors.RShoulderRoll.name, Math.toRadians(-90), speed);
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(45), speed);
                        p.setAngles(motors.RElbowRoll.name, Math.toRadians(90), speed);

                        break;
                    case 0:
                    case 2:
                    case 3:
                    case 5:
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(45), speed);
                        p.waitUntilMoveIsFinished();
                        Thread.sleep(0_700);
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(0), speed);
                        p.setAngles(motors.RElbowRoll.name, Math.toRadians(45), speed);
                        p.waitUntilMoveIsFinished();
                        Thread.sleep(0_700);
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(-45), speed);
                        p.setAngles(motors.RElbowRoll.name, Math.toRadians(90), speed);
                        p.waitUntilMoveIsFinished();
                        Thread.sleep(0_700);
                        rechtsSchritt(1);
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(0), speed);
                        p.setAngles(motors.RElbowRoll.name, Math.toRadians(135), speed);
                        p.setAngles(motors.RShoulderRoll.name, Math.toRadians(-45), speed);
                        p.waitUntilMoveIsFinished();
                        Thread.sleep(0_700);
                        p.setAngles(motors.RShoulderRoll.name, Math.toRadians(-90), speed);
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(45), speed);
                        p.setAngles(motors.RElbowRoll.name, Math.toRadians(90), speed);
                        break;
                    default:
                        break;
                }
                durchlauf++;
            }

            durchlauf = 0;
            while(durchlauf < 4){
                switch (durchlauf) {
                    case 1:
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(45), speed);
                        p.waitUntilMoveIsFinished();
                        Thread.sleep(0_700);
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(0), speed);
                        p.setAngles(motors.RElbowRoll.name, Math.toRadians(45), speed);
                        p.waitUntilMoveIsFinished();
                        Thread.sleep(0_700);
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(-45), speed);
                        p.setAngles(motors.RElbowRoll.name, Math.toRadians(90), speed);
                        p.waitUntilMoveIsFinished();
                        Thread.sleep(0_700);
                        linksSchritt(1);
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(0), speed);
                        p.setAngles(motors.RElbowRoll.name, Math.toRadians(90), speed);
                        p.setAngles(motors.RShoulderRoll.name, Math.toRadians(-45), speed);
                        p.waitUntilMoveIsFinished();
                        Thread.sleep(0_700);
                        p.setAngles(motors.RShoulderRoll.name, Math.toRadians(-90), speed);
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(45), speed);
                        p.setAngles(motors.RElbowRoll.name, Math.toRadians(90), speed);

                        break;
                    case 0:
                    case 2:
                    case 3:
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(45), speed);
                        p.waitUntilMoveIsFinished();
                        Thread.sleep(0_700);
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(0), speed);
                        p.setAngles(motors.RElbowRoll.name, Math.toRadians(45), speed);
                        p.waitUntilMoveIsFinished();
                        Thread.sleep(0_700);
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(-45), speed);
                        p.setAngles(motors.RElbowRoll.name, Math.toRadians(90), speed);
                        p.waitUntilMoveIsFinished();
                        Thread.sleep(0_700);
                        rechtsSchritt(1);
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(0), speed);
                        p.setAngles(motors.RElbowRoll.name, Math.toRadians(90), speed);
                        p.setAngles(motors.RShoulderRoll.name, Math.toRadians(-45), speed);
                        p.waitUntilMoveIsFinished();
                        Thread.sleep(0_700);
                        p.setAngles(motors.RShoulderRoll.name, Math.toRadians(-90), speed);
                        p.setAngles(motors.RElbowYaw.name, Math.toRadians(45), speed);
                        p.setAngles(motors.RElbowRoll.name, Math.toRadians(90), speed);
                        break;
                    default:
                        break;
                }
                durchlauf++;
            }
            armDrehen(2);
            p.waitUntilMoveIsFinished();
            Thread.sleep(0_700);
            int ThreadSleep = 0_700;
            speed = 0.2f;
            p.setAngles(motors.RElbowYaw.name, Math.toRadians(0), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(45), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(ThreadSleep);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(0), speed);
            p.setAngles(motors.RShoulderRoll.name, Math.toRadians(0), speed);
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(-15), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(ThreadSleep);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(90), speed);
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(-75), speed);
            p.setAngles(motors.RShoulderRoll.name, Math.toRadians(-9), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(ThreadSleep);
            p.setAngles(motors.RShoulderRoll.name, Math.toRadians(-90), speed);
            p.setAngles(motors.RElbowYaw.name, Math.toRadians(45), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(90), speed);
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(-90), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(ThreadSleep);
            p.setAngles(motors.RElbowYaw.name, Math.toRadians(0), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(45), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(ThreadSleep);
            p.setAngles(motors.RShoulderRoll.name, Math.toRadians(18), speed);
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(40), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(40), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(ThreadSleep);

            speed = 0.2f;
            p.setAngles(motors.RShoulderRoll.name, Math.toRadians(-30), speed);
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(90), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(90), speed);

            p.setAngles(motors.LShoulderRoll.name, Math.toRadians(30), speed);
            p.setAngles(motors.LShoulderPitch.name, Math.toRadians(90), speed);
            p.setAngles(motors.LElbowRoll.name, Math.toRadians(-90), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(1_400);

            fussTritt(4);

            speed = 0.1f;
            p.setAngles(motors.RHipRoll.name, Math.toRadians(-90), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(1_400);
            p.setAngles(motors.RHipRoll.name, -0.4f, speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(1_400);

            p.setAngles(motors.LShoulderRoll.name, Math.toRadians(-18), speed);
            p.setAngles(motors.LShoulderPitch.name, Math.toRadians(50), speed);
            p.setAngles(motors.LElbowRoll.name, Math.toRadians(-40), speed);

            p.setAngles(motors.RShoulderRoll.name, Math.toRadians(18), speed);
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(40), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(40), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(1_400);

            p.moveTo(0f, 0f, (float)Math.toRadians(45));
            p.waitUntilWalkIsFinished();

            speed = 0.2f;

            p.setAngles(motors.HeadYaw.name, Math.toRadians(-45 ), speed);
            p.setAngles(motors.HeadPitch.name, Math.toRadians(-20), speed);

            p.setAngles(motors.RKneePitch.name, Math.toRadians(90), speed);
            p.setAngles(motors.RHipPitch.name, Math.toRadians(-90), speed);

            p.setAngles(motors.LKneePitch.name, Math.toRadians(40), speed);
            p.setAngles(motors.LHipPitch.name, Math.toRadians(-90), speed);
            p.setAngles(motors.LAnklePitch.name, Math.toRadians(20), speed);

            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(50), speed);
            p.setAngles(motors.RShoulderRoll.name, Math.toRadians(06), speed);
            p.setAngles(motors.RElbowYaw.name, Math.toRadians(0), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(60), speed);
            p.setAngles(motors.RWristYaw.name, Math.toRadians(-55), speed);

            p.setAngles(motors.LShoulderPitch.name, Math.toRadians(50), speed);
            p.setAngles(motors.LShoulderRoll.name, Math.toRadians(26), speed);
            p.setAngles(motors.LElbowYaw.name, Math.toRadians(0), speed);
            p.setAngles(motors.LElbowRoll.name, Math.toRadians(-75), speed);
            p.setAngles(motors.LWristYaw.name, Math.toRadians(55), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(1_400);

            Kniebeuge(4);

            KnieDrehen(2);

            FussDrehen(3);

            p.moveTo(0f, 0f, (float)Math.toRadians(-45));
            p.setAngles(motors.HeadYaw.name, Math.toRadians(0 ), speed);
            p.setAngles(motors.HeadPitch.name, Math.toRadians(0), speed);
            p.waitUntilWalkIsFinished();

            p.setAngles(motors.LHipRoll.name, 0.4f, speed);
            p.setAngles(motors.LAnkleRoll.name, -0.4f, speed);

            p.setAngles(motors.RHipRoll.name, -0.4f, speed);
            p.setAngles(motors.RAnkleRoll.name, 0.4f, speed);

            p.setAngles(motors.RShoulderRoll.name, Math.toRadians(0), speed);
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(40), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(85), speed);
            p.setAngles(motors.RElbowYaw.name, Math.toRadians(20), speed);
            p.setAngles(motors.RWristYaw.name, Math.toRadians(-40), speed);

            p.setAngles(motors.LShoulderRoll.name, Math.toRadians(0), speed);
            p.setAngles(motors.LShoulderPitch.name, Math.toRadians(40), speed);
            p.setAngles(motors.LElbowRoll.name, Math.toRadians(-85), speed);
            p.setAngles(motors.LElbowYaw.name, Math.toRadians(-20), speed);
            p.setAngles(motors.LWristYaw.name, Math.toRadians(40), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(2_000);

            BrustSchlag(3);

            p.waitUntilMoveIsFinished();
            Thread.sleep(0_500);

            p.setAngles(motors.RElbowRoll.name, Math.toRadians(90), speed);
            p.setAngles(motors.RShoulderRoll.name, Math.toRadians(-30), speed);
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(-90), speed);
            p.setAngles(motors.RWristYaw.name, Math.toRadians(100), speed);
            p.openHand("LHand");
            p.waitUntilMoveIsFinished();
            Thread.sleep(2_000);

            ElbogenSchlag(3);

            p.closeHand("LHand");
            p.setAngles(motors.LShoulderRoll.name, Math.toRadians(-12), speed);
            p.setAngles(motors.LShoulderPitch.name, Math.toRadians(70), speed);
            p.setAngles(motors.LElbowYaw.name, Math.toRadians(13), speed);
            p.setAngles(motors.LElbowRoll.name, Math.toRadians(-3), speed);
            p.setAngles(motors.LWristYaw.name, Math.toRadians(-13), speed);

            p.setAngles(motors.RShoulderRoll.name, Math.toRadians(12), speed);
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(70), speed);
            p.setAngles(motors.RElbowYaw.name, Math.toRadians(-13), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(3), speed);
            p.setAngles(motors.RWristYaw.name, Math.toRadians(13), speed);

            p.waitUntilMoveIsFinished();
            Thread.sleep(1_400);

            p.setAngles(motors.LShoulderRoll.name, Math.toRadians(40), speed);
            p.setAngles(motors.LShoulderPitch.name, Math.toRadians(60), speed);
            p.setAngles(motors.LElbowYaw.name, Math.toRadians(15), speed);
            p.setAngles(motors.LElbowRoll.name, Math.toRadians(-5), speed);
            p.setAngles(motors.LWristYaw.name, Math.toRadians(-15), speed);

            p.setAngles(motors.RShoulderRoll.name, Math.toRadians(-40), speed);
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(60), speed);
            p.setAngles(motors.RElbowYaw.name, Math.toRadians(-15), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(5), speed);
            p.setAngles(motors.RWristYaw.name, Math.toRadians(15), speed);

            p.waitUntilMoveIsFinished();
            Thread.sleep(1_400);

            p.setAngles(motors.LShoulderRoll.name, Math.toRadians(-12), speed);
            p.setAngles(motors.LShoulderPitch.name, Math.toRadians(30), speed);
            p.setAngles(motors.LElbowYaw.name, Math.toRadians(10), speed);
            p.setAngles(motors.LElbowRoll.name, Math.toRadians(-90), speed);
            p.setAngles(motors.LWristYaw.name, Math.toRadians(-13), speed);

            p.setAngles(motors.RShoulderRoll.name, Math.toRadians(-5), speed);
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(50), speed);
            p.setAngles(motors.RElbowYaw.name, Math.toRadians(-13), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(70), speed);
            p.setAngles(motors.RWristYaw.name, Math.toRadians(-30), speed);

            p.moveTo(0f, 0f, (float)Math.toRadians(45));
            p.setAngles(motors.HeadYaw.name, Math.toRadians(-45 ), speed);
            p.setAngles(motors.HeadPitch.name, Math.toRadians(-20), speed);
            p.waitUntilWalkIsFinished();

            p.setAngles(motors.LHipPitch.name, Math.toRadians(0), speed);
            p.setAngles(motors.LHipRoll.name, Math.toRadians(0), speed);
            p.setAngles(motors.LHipYawPitch.name, Math.toRadians(0), speed);
            p.setAngles(motors.LAnklePitch.name, Math.toRadians(0), speed);
            p.setAngles(motors.LAnkleRoll.name, Math.toRadians(0), speed);
            p.setAngles(motors.LKneePitch.name, Math.toRadians(0), speed);


            p.setAngles(motors.RHipPitch.name, Math.toRadians(0), speed);
            p.setAngles(motors.RHipRoll.name, Math.toRadians(0), speed);
            p.setAngles(motors.RHipYawPitch.name, Math.toRadians(0), speed);
            p.setAngles(motors.RAnklePitch.name, Math.toRadians(0), speed);
            p.setAngles(motors.RAnkleRoll.name, Math.toRadians(0), speed);
            p.setAngles(motors.RKneePitch.name, Math.toRadians(0), speed);

            p.waitUntilMoveIsFinished();
            Thread.sleep(1_400);

            p.setAngles(motors.RHipPitch.name, Math.toRadians(-40), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(5), speed);
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(120), speed);

            p.setAngles(motors.LElbowRoll.name, Math.toRadians(-5), speed);
            p.setAngles(motors.LShoulderPitch.name, Math.toRadians(30), speed);

            p.waitUntilMoveIsFinished();
            Thread.sleep(2_500);

            p.setAngles(motors.RKneePitch.name, Math.toRadians(90), speed);
            p.setAngles(motors.RHipPitch.name, Math.toRadians(-90), speed);
            p.setAngles(motors.RAnklePitch.name, Math.toRadians(-30), speed);
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(7), speed);
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(20), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(90), speed);
            p.setAngles(motors.RElbowYaw.name, Math.toRadians(90), speed);
            p.setAngles(motors.RWristYaw.name, Math.toRadians(40), speed);

            p.setAngles(motors.LKneePitch.name, Math.toRadians(40), speed);
            p.setAngles(motors.LHipPitch.name, Math.toRadians(-90), speed);
            p.setAngles(motors.LAnklePitch.name, Math.toRadians(20), speed);
            p.setAngles(motors.LShoulderPitch.name, Math.toRadians(90), speed);


        } catch(Exception err) {
            err.printStackTrace();
        }
    }
    public void ElbogenSchlag(int Anzahl)throws Exception{
        float speed = 0.2f;
        int Thread_sleep = 0_700;
        ALMotion p = new ALMotion(application.session());
        for(int i=0;i<Anzahl;i++) {
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(-60), speed);
            p.setAngles(motors.LShoulderRoll.name, Math.toRadians(-15), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(Thread_sleep);
            p.setAngles(motors.RShoulderPitch.name, Math.toRadians(-90), speed);
            p.setAngles(motors.LShoulderRoll.name, Math.toRadians(0), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(Thread_sleep);

        }
    }

    public void BrustSchlag(int Anzahl)throws Exception{
        float speed = 0.2f;
        int Thread_sleep = 0_700;
        ALMotion p = new ALMotion(application.session());
        for(int i=0;i<Anzahl;i++) {
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(60), speed);
            p.setAngles(motors.LElbowRoll.name, Math.toRadians(-60), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(Thread_sleep);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(85), speed);
            p.setAngles(motors.LElbowRoll.name, Math.toRadians(-85), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(Thread_sleep);
        }
    }

    public void FussDrehen(int Anzahl) throws Exception{
        float speed = 0.2f;
        int Thread_sleep = 0_700;
        ALMotion p = new ALMotion(application.session());
        for(int i=0;i<Anzahl;i++) {
            p.setAngles(motors.RHipRoll.name, Math.toRadians(-20), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(Thread_sleep);
            p.setAngles(motors.RHipRoll.name, Math.toRadians(0), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(Thread_sleep);
        }
    }

    public void KnieDrehen(int Anzahl) throws Exception{
        float speed = 0.15f;
        int Thread_sleep = 1_050;
        ALMotion p = new ALMotion(application.session());
        for(int i=0;i<Anzahl;i++) {
            p.setAngles(motors.RHipRoll.name, Math.toRadians(-25), speed);
            p.setAngles(motors.RAnkleRoll.name, Math.toRadians(23), speed);
            p.setAngles(motors.RAnklePitch.name, Math.toRadians(-15), speed);
            p.setAngles(motors.RKneePitch.name, Math.toRadians(70), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(Thread_sleep);
            p.setAngles(motors.RHipRoll.name, Math.toRadians(0), speed);
            p.setAngles(motors.RAnkleRoll.name, Math.toRadians(0), speed);
            p.setAngles(motors.RAnklePitch.name, Math.toRadians(-30), speed);
            p.setAngles(motors.RKneePitch.name, Math.toRadians(90), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(Thread_sleep);
        }
    }

    public void Kniebeuge(int Anzahl) throws Exception{
        float speed = 0.2f;
        int Thread_sleep = 0_700;
        ALMotion p = new ALMotion(application.session());
        for(int i=0;i<Anzahl;i++) {
            p.setAngles(motors.RKneePitch.name, Math.toRadians(100), speed);
            p.setAngles(motors.LKneePitch.name, Math.toRadians(50), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(Thread_sleep);
            p.setAngles(motors.RKneePitch.name, Math.toRadians(90), speed);
            p.setAngles(motors.LKneePitch.name, Math.toRadians(40), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(Thread_sleep);

        }
    }

    public void fussTritt(int Anzahl) throws Exception{
        float speed = 0.2f;
        ALMotion p = new ALMotion(application.session());
        for(int i=0;i<Anzahl;i++) {
            p.setAngles(motors.RHipRoll.name, Math.toRadians(-40), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(0_700);
            p.setAngles(motors.RHipRoll.name, -0.4f, speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(0_700);
        }
    }

    public void armDrehen(int Anzahl) throws Exception{
        float speed = 0.2f;
        ALMotion p = new ALMotion(application.session());
        for(int i=0;i<Anzahl;i++) {
            p.setAngles(motors.RElbowYaw.name, Math.toRadians(45), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(0_700);
            p.setAngles(motors.RElbowYaw.name, Math.toRadians(0), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(45), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(0_700);
            p.setAngles(motors.RElbowYaw.name, Math.toRadians(-45), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(90), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(0_700);
            p.setAngles(motors.RElbowYaw.name, Math.toRadians(0), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(135), speed);
            p.setAngles(motors.RShoulderRoll.name, Math.toRadians(-45), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(0_700);
            p.setAngles(motors.RShoulderRoll.name, Math.toRadians(-90), speed);
            p.setAngles(motors.RElbowYaw.name, Math.toRadians(45), speed);
            p.setAngles(motors.RElbowRoll.name, Math.toRadians(90), speed);
        }
    }

    public void rechtsSchritt(int Anzahl) throws Exception{
        float speed = 0.5f;
        ALMotion p = new ALMotion(application.session());
        for(int i=0;i<Anzahl;i++) {
            p.setAngles(motors.RKneePitch.name, Math.toRadians(35), speed);
            p.setAngles(motors.RHipPitch.name, Math.toRadians(-25), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(0_600);
            p.setAngles(motors.RKneePitch.name, Math.toRadians(0), speed);
            p.setAngles(motors.RHipPitch.name, Math.toRadians(0), speed);
            p.waitUntilMoveIsFinished();
        }
    }
    public void linksSchritt(int Anzahl) throws Exception{
        float speed = 0.5f;
        ALMotion p = new ALMotion(application.session());
        for(int i=0;i<Anzahl;i++) {
            p.setAngles(motors.LKneePitch.name, Math.toRadians(35), speed);
            p.setAngles(motors.LHipPitch.name, Math.toRadians(-25), speed);
            p.waitUntilMoveIsFinished();
            Thread.sleep(0_600);
            p.setAngles(motors.LKneePitch.name, Math.toRadians(0), speed);
            p.setAngles(motors.LHipPitch.name, Math.toRadians(0), speed);
            p.waitUntilMoveIsFinished();
        }
    }

    public void resetMotion() throws Exception {
        ALMotion p = new ALMotion(application.session());
        for(motors nMotors : motors.values()) {
            p.setAngles(nMotors.name, 0f, 1f);
        }
        p.waitUntilMoveIsFinished();
        Thread.sleep(1000);
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean useArgs() {
        return false;
    }
}
