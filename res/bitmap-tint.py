import pygame, sys,os
import math  # need it for the trig
from pygame.locals import *
import random as r

pygame.init()

width = 500
height = 500
window = pygame.display.set_mode((width, height))
pygame.display.set_caption('tint bitmap')
width = window.get_width()
height = window.get_height()

# --------------------------------------------
# generic behind the scenes ops stuff

# blank screen
def clear():
	screen.fill(0)


# handle some keyboard and mouse UI stuff
def input(events):
	global angle
	global dangle
	for event in events: 
		if event.type == pygame.KEYDOWN and ((event.key == pygame.K_q )):
			pygame.display.quit()
			sys.exit()
		elif event.type == pygame.KEYDOWN and event.key == pygame.K_DELETE:
			clear()
		elif event.type == pygame.QUIT:
			pygame.display.quit()
			sys.exit()
	return 0

def main():
	global mysurf
	mysurf = pygame.image.load("./star-small.png")
	ox = mysurf.get_width() / 2
	oy = mysurf.get_height() / 2
	for i in range(1):
			mouse = pygame.mouse.get_pos()
			mx = mouse[0]
			my = mouse[1]
			r = float(width-mx) / width * 255
			g = float(my) / height * 255
			b = float(mx + my) / (width + height) * 255
			imgTemp = mysurf.copy()
			imgTemp.fill((r,g,b),special_flags=BLEND_RGBA_MULT)
					
			screen.blit(imgTemp,(mx - ox ,my - oy))

	
# === Main ======================================
screen = pygame.display.get_surface()
while (1==1): 
	input(pygame.event.get())
	pygame.display.flip()
	#clear()
	main()

