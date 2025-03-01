from turtle import*
import random as r
def drawmaze(M,N):
    def jumpto(x,y):
        up()
        goto(x,y)
        down()
    def Access(x,y):
        color("black")
        jumpto(x,y)
        for i in range(4):
            forward(size/6)
            up()
            forward(size/6*4)
            down()
            forward(size/6)
            right(90)
    def Wall(x,y,size):
        color("red")
        jumpto(x,y)
        for i in range(4):
            forward(size)
            right(90)
        goto(x+size,y-size)
        jumpto(x,y-size)
        goto(x+size,y)
    m=[]
    for i in range(M):
        list=[]
        for j in range(N):
            list.append(r.randint(0,1))
        m.append(list)
    speed("fast")
    size=40
    startX=-len(m)/2*size
    startY=len(m)/2*size
    for i in range(len(m)):
        for j in range(len(m[i])):
            if m[i][j]==0:
                Access(startX+j*size,startY-i*size)
            else:
                Wall(startX+j*size,startY-i*size,size)
    jumpto(startX,startY)
    s=Screen()
    s.exitonclick()
