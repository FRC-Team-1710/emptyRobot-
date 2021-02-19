package frc.robot;

public class FlyPID{
    
        
       

    public static double FlywheelPID(double p, double i, double d, double f, double desOut, double flyOut){

    double actOut = flyOut;
    double error = desOut - actOut;
    boolean firstRun = true;
    double lastAct = 0;
    double errorSum = 0;
    double maxError = 5500;
    double maxIVal = 1; 
    double maxOut = 1; //max value a .set(speed) command will take is 1
    double minOut = -1; //min value a .set(speed) command will take is -1

    if(i != 0){
        maxError = maxIVal / i;
    }

    double pVal = p * error; //setting P Value

    double ffVal = f * desOut; //setting FF Value
    if(firstRun){ //the first run won't have a last output so we set it
        lastAct = actOut;
        firstRun = false;
    }

    double dVal = -d * (actOut - lastAct); //setting D Value
    lastAct = actOut; 

    double iVal = i * errorSum; //setting I Value
    if(maxIVal != 0){ //making sure the I Value is within the limits
        iVal = constrain(iVal ,-maxIVal ,maxIVal); 
    }

    double PIDOutput = pVal + iVal + dVal + ffVal; //adding everthing up to get the output

    if(minOut != maxOut && !bounded(actOut, minOut, maxOut) ){
        errorSum = error;
    }
    else if(maxIVal != 0){
		errorSum = constrain(errorSum + error, -maxError, maxError);
    }    
    else{
        errorSum += error;
    }
    if(minOut != maxOut){ 
        PIDOutput = constrain(PIDOutput, minOut, maxOut);
    }
	return PIDOutput;
}
public static double constrain(double value, double min, double max){
    if(value > max){ return max;}
    if(value < min){ return min;}
    return value;
}
public static boolean bounded(double value, double min, double max){
    return (min < value) && (value < max);
}
}