#Test program to activate sensors by calling their files.  Tests that files and hardware work
#Libraries
import RPi.GPIO as GPIO
import time
import First_Sonar  #allows this program to call another python program to get data from first sonar sensor
import Second_Sonar  #allows this program to call another python program to get data from second sonar sensor
import bluetooth

#GPIO Mode (BOARD / BCM)
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

if__name__ == '__main__' :
    try:

        while true:

            print("{0:.0f}".format(First_Sonar.distance1()))
            print("{0:.0f}".format(Second_Sonar.distance2()))
            time.sleep(1)

    #Stop program with Control + C
    except KeyboardInterrupt:
        GPIO.cleanup()
        print("Program Stopped by Pi")
