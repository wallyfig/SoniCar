#Proof of concept code: When sensor readings hit a certain threshold, actviate the LED
#Sends distance readings to Android Phone(BlueTerm) using Bluetooth
#Libraries
import bluetooth
import RPi.GPIO as GPIO
import time

 
#GPIO Mode (BOARD / BCM)
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
 
#set GPIO Pins
#Pins 20,21 (trigger, echo) are for sensor 1.  8,7 are for sensor 2
GPIO_TRIGGER = 20
GPIO_ECHO = 21

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

#Sets up method of Bluetooth conection with socket/port
server_sock=bluetooth.BluetoothSocket( bluetooth.RFCOMM )

port = 1
server_sock.bind(('',port))
server_sock.listen(1)

#Loop to constantly run sensor and determine what to say regarding distance readings
#Distance readings are formated to no decimal places for display 
if __name__ == '__main__':
    
    #Establishes and  confirms connection with Bluetooth device
    client_sock,address = server_sock.accept()
    print('Accepted connection from ',address)
    
    try:
        
        data = client_sock.recv(1024)
        
        while True:
            dist = distance()
            time.sleep(1)

            if (dist > 25 and dist < 3000):
                print ("Distance = %.0f cm" % dist)
                data = str(("{:.0f}".format(distance())))
                GPIO.output(GPIO_LED,GPIO.LOW)

            elif (dist > 15 and dist <= 25):
                print ("Caution - Distance = %.0f cm" % dist)
                data = str(("{:.0f}".format(distance())))
                GPIO.output(GPIO_LED,GPIO.HIGH)

            elif (dist <= 15):
                print ("WARNING - Distance = %.0f cm" % dist)
                data = str(("{:.0f}".format(distance())))

            else: 
                GPIO.output(GPIO_LED,GPIO.LOW)

            #Sends readings to Bluetooth device    
            client_sock.send(data)
            print "sending [%s]" % data
                
    # Reset by pressing CTRL + C
    except KeyboardInterrupt:
        GPIO.output(GPIO_LED,GPIO.LOW)
        GPIO.cleanup()
        print("Measurement stopped by User")
        
        client_sock.close()
        server_sock.close()

