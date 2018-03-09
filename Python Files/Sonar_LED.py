#Proof of concept code: When sensor readings hit a certain threshold, actviate the LED
#Libraries
import RPi.GPIO as GPIO
import time

 
#GPIO Mode (BOARD / BCM)
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
 
#set GPIO Pins
#Pins 20,21 (trigger, echo) are for sensor 1.  8,7 are for sensor 2
GPIO_TRIGGER = 8
GPIO_ECHO = 7

GPIO_LED = 18
 
#set GPIO direction (IN / OUT)
GPIO.setup(GPIO_TRIGGER, GPIO.OUT)
GPIO.setup(GPIO_ECHO, GPIO.IN)

GPIO.setup(GPIO_LED,GPIO.OUT)

def Blink(numTimes,speed):
    for i in range(0,numTimes):
        GPIO.output(GPIO_LED,True)
        time.sleep(speed)

        GPIO.output(GPIO_LED,False)
        time.sleep(speed)

def distance():
    # set Trigger to HIGH
    GPIO.output(GPIO_TRIGGER, True)
 
    # set Trigger after 0.01ms to LOW
    time.sleep(0.00001)
    GPIO.output(GPIO_TRIGGER, False)
 
    StartTime = time.time()
    StopTime = time.time()
 
    # save StartTime
    while GPIO.input(GPIO_ECHO) == 0:
        StartTime = time.time()
 
    # save time of arrival
    while GPIO.input(GPIO_ECHO) == 1:
        StopTime = time.time()
 
    # time difference between start and arrival
    TimeElapsed = StopTime - StartTime
    # multiply with the sonic speed (34300 cm/s)
    # and divide by 2, because there and back
    distance = (TimeElapsed * 34300) / 2
 
    return distance

#Loop to constantly run sensro and determine what to say regarding distance readings
#Distance readings are formated to single decimal place for display 
if __name__ == '__main__':
    try:
        while True:
            dist = distance()
            time.sleep(1)

            #Normal operation distances
            if (dist > 25 and dist < 3000):
                print ("Distance = %.1f cm" % dist)
                GPIO.output(GPIO_LED,GPIO.LOW)

            #Warning the user they are getting close to an object
            elif (dist > 15 and dist <= 25):
                print ("Caution - Distance = %.1f cm" % dist)
                GPIO.output(GPIO_LED,GPIO.HIGH)
            
            #Collison will happen soon, do something about it
            elif (dist <= 15):
                print ("WARNING - Distance = %.1f cm" % dist)
                Blink(3,0.1)
            
            #Keeps LED off unless needed
            else: 
                GPIO.output(GPIO_LED,GPIO.LOW)
                
    # Reset by pressing CTRL + C
    except KeyboardInterrupt:
        GPIO.output(GPIO_LED,GPIO.LOW)
        GPIO.cleanup()
        print("Measurement stopped by User")

