#Main program to activate sensors and their text warnings
#Libraries
import RPi.GPIO as GPIO
import time
import First_Sonar  #allows this program to call another python program to get data from first sonar sensor
import Second_Sonar  #allows this program to call another python program to get data from second sonar sensor
import bluetooth

#GPIO Mode (BOARD / BCM)
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
 
server_sock=bluetooth.BluetoothSocket( bluetooth.RFCOMM )
port = 1
server_sock.bind(('',port))
server_sock.listen(1)


while True:    
    
    print "Waiting for connection on RFCOMM channel %d" % port
    client_sock,address = server_sock.accept()
    print('Accepted connection from ',address)
    
    try:
        
        data = client_sock.recv(1024)
        #print (First_Sonar.distance1())
        #print (Second_Sonar.distance2())
        
        print "received [%s]" % data
        
        if data == '1':
            data = str(First_Sonar.distance1() + "  " + Second_Sonar.distance2()) 
            time.sleep(1)
            
        elif data == '0':
            data = ""
            time.sleep(1)
            
        elif data == 'e':
            data = "Exit"
            time.sleep(0)
            GPIO.cleanup()
            print("Program stopped by phone")
                
            client_sock.close()
            server_sock.close()
            break
        
        else:
            data = "Junk"
            
        client_sock.send(data)
        print "sending [%s]" % data
    
    except IOError:
	pass
    
    # Reset and Stop by pressing CTRL + C
    except KeyboardInterrupt:
        GPIO.cleanup()
        print("Program stopped by Pi")
        
        client_sock.close()
        server_sock.close()

