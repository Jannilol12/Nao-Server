package nao.functions;

public enum motors {
	HeadYaw("HeadYaw"),
	HeadPitch("HeadPitch"),
	LShoulderPitch("LShoulderPitch"),
	LShoulderRoll("LShoulderRoll"),
	LElbowYaw("LElbowYaw"),
	LElbowRoll("LElbowRoll"),
	LWristYaw("LWristYaw"),
	LHand("LHand"),
	LHipYawPitch("LHipYawPitch"),
	LHipRoll("LHipRoll"),
	LHipPitch("LHipPitch"),
	LKneePitch("LKneePitch"),
	LAnklePitch("LAnklePitch"),
	LAnkleRoll("LAnkleRoll"),
	
	RHipYawPitch("RHipYawPitch"),
	RHipRoll("RHipRoll"),
	RHipPitch("RHipPitch"),
	RKneePitch("RKneePitch"),
	RAnklePitch("RAnklePitch"),
	RAnkleRoll("RAnkleRoll"),
	RShoulderPitch("RShoulderPitch"),
	RShoulderRoll("RShoulderRoll"),
	RElbowYaw("RElbowYaw"),
	RElbowRoll("RElbowRoll"),
	RWristYaw("RWristYaw"),
	RHand("RHand");
	
	public final String name;
	motors(String name){
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
