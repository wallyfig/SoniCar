#Proof of concept code: Sonar sensor reads distance then activates warning light at certain threshold.
#Sends Readings to console and Android phone with blueterm
#Libraries
import bluetooth
import RPi.GPIO as GPIO
import time

 
#GPIO Mode (BOARD / BCM)
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
 
#set GPIO Pins
GPIO_TRIGGER = 21
GPIO_ECHO = 20

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

#Sets up Bluetooth to coneect to phone
server_sock=bluetooth.BluetoothSocket( bluetooth.RFCOMM )

port = 1
server_sock.bind(('',port))
server_sock.listen(1)

uuid = "d4f0fd64-ad9d-4cfd-aa76-8d3541fbf008"

#advertise_service( server_sock, "AquaPiServer",
#                   service_id = uuid,
#                   service_classes = [ uuid, SERIAL_PORT_CLASS ],
#                   profiles = [ SERIAL_PORT_PROFILE ],
#                 )


if __name__ == '__main__':
    
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
                
            client_sock.send(data)
            print "sending [%s]" % data
                
    # Reset by pressing CTRL + C
    except KeyboardInterrupt:
        GPIO.output(GPIO_LED,GPIO.LOW)
        GPIO.cleanup()
        print("Measurement stopped by User")
        
        client_sock.close()
        server_sock.close()

