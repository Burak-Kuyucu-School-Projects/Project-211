Project 211
===========

### Introduction
This project is created for writing a GUI to draw shapes.

### Description
GUI contains many parts. In the following, they are described:

* _**ButtonGroup:**_ It is a group of JRadioButtons for selecting the drawn shape. Three shapes can be selected:
    
    * Rectangle
    * Circle
    * Line
    
* _**JComboBox:**_  It is a drop down list for selecting color of the drawn shape. Five colors can be selected:
    
    * Blue
    * Yellow
    * Black
    * Green
    * Red
    
* _**JScrollPane:**_  It is a scroll list named _"Shapes"_ for listing drawn shapes.<br>

* _**JButton:**_  There are five buttons in the GUI.

    * **Undo:** It removes the last shape from the _"Shapes"_.
    * **Clear:** It removes all shapes from the _"Shapes"_.
    * **Animate:** It changes color of the selected shape in every 500 milliseconds. Color of the shape is changed to blue, yellow, black, green, red respectively.
    * **Stop:** It stops the animation effect of the selected shape and returns its color to its initial color.
    * **Delete:** It deletes the selected shape.

### Saving
When GUI is closed, all shapes are saved into the _"savings.txt"_ file. It consists of the rows like the following:<br>

> Rectangle&432,156&611,255&GREEN&false

**Format:** _shape_ & _first coordinate_ & _second coordinate_ & _color_ & _is animated_

### License
Copyright 2019 Burak Kuyucu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


