#Testing Porgram for modular sensors (plugin and use what you need)
#Tell program to read certain sensors and gather their data only instead of reading everything then filtering down
#2 ways to do this (so far), comment out one not in use
#Libraries
import RPi.GPIO as GPIO
import time
import First_Sonar  #allows this program to call another python program to get data from first sonar sensor
import Second_Sonar  #allows this program to call another python program to get data from second sonar sensor

#GPIO Mode (BOARD / BCM)
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

#Method 1: Outputs one reading at a time, sends input request every time       
while True:          
    
    try:
	data = int(input('Enter 1, 2, or 3: '))    
	print "Received [%s]" % data

        if data == 1:
           print (First_Sonar.distance1())
           
        if data == 2:
           print (Second_Sonar.distance2())
           
        if data == 3:
           print (First_Sonar.distance1() + " " + Second_Sonar.distance2())
            
    except IOError:
	    pass

    except KeyboardInterrupt:

	GPIO.cleanup()
        print("\n Program stopped by Pi")
	break        

#Method 2: Constant output of sensor readings, very fast, stop with Control + C
#if __name__ == '__main__':
    
  
#    try:
        
#	data = int(input('Enter 1, 2, or 3: '))    
#	print "Received [%s]" % data

#        while True:

#            if data == 1:
#               print (First_Sonar.distance1())
           
#            if data == 2:
#               print (Second_Sonar.distance2())
           
#            if data == 3:
#               print (First_Sonar.distance1() + " " + Second_Sonar.distance2())
    
#    except IOError:
#        pass
    
#    except KeyboardInterrupt:
#        GPIO.cleanup()
#        print("\n Program stopped by Pi")
