#Testing Program to activate sensors by calling their files
#Also tests ability to send data by Bluetooth, comment out those sections if only checking sensor functionality
#Libraries
import RPi.GPIO as GPIO
import time
import First_Sonar  #allows this program to call another python program to get data from first sonar sensor
import Second_Sonar  #allows this program to call another python program to get data from second sonar sensor
from bluetooth import*

#GPIO Mode (BOARD / BCM)
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

#BT Code 1 Start
#server_sock = BluetoothSocket( RFCOMM )

#port = server_sock.getsockname()[1]    #Looks for any open port/socket to connect with
#server_sock.bind(('',bluetooth.PORT_ANY))

#server_sock.listen(1)

#uuid = "94f39d29-7d6d-437d-973b-fba39e49d4ee"
#BT Code 1 End

if __name__ == '__main__':
    
    #BT Code 2 Start    
    #print "Waiting for connection on RFCOMM channel"
    #client_sock,address = server_sock.accept()
    #print('Accepted connection from ',address)
    #BT Code 2 End
    
    try:
        
        #BT Code 3 Start
        #data = client_sock.recv(1024)
        #BT Code 3 End

        while True:

            print (First_Sonar.distance1() + " " + Second_Sonar.distance2())
            
            #BT Code 4 Start
            #data = str(First_Sonar.distance1() + "  " + Second_Sonar.distance2())
            #client_sock.send(data)
            #print "sending [%s]" % data
            #BT Code 4 End
            time.sleep(1)
            
                
    # Reset by pressing CTRL + C
    except KeyboardInterrupt:
        GPIO.cleanup()

        #BT Code 5 Start
        #client_sock.close()
	#server_sock.close()
	#BT Code 5 End
	
        print("\n Program stopped by Pi")

