Minesweeper
===========

Minesweeper is a simple game, which became very popular on Windows. I really liked playing it and wanted to implement my own version, just for fun. So here it is, Classic Minesweeper in Java. (In progress...)

You can learn more about the game online:

- https://en.wikipedia.org/wiki/Minesweeper_(video_game)
- https://en.wikipedia.org/wiki/Microsoft_Minesweeper
- http://www.minesweeper.info/wiki/Windows_Minesweeper


Difficulty levels
-----------------

- Beginner (8x8, 10 mines) -> mine density = 0.15625
- Intermediate (16x16, 40 mines) -> mine density = 0.15625
- Expert (24x24, 99 mines) -> mine density = 0.171875

Game start
----------

The game and timer start when the player clears the first cell.

To prevent the player from losing on the first move, mines are placed after the first cell is cleared.

To place the mines, a sample with uniform distribution is selected from all the cells excluding the first cleared cell.


Player actions
--------------

- Clear cell (click)

- Toggle mark (Alt + click)
  Cycle between no mark, flag, and question mark.
  Flags may be used for cells where the player believes there is a mine.
  Question marks may be used for cells where the player believes there could be a mine, but is not sure if there is.
  Note that allowing question marks makes it slower to remove a flag, so there is a menu option to enable or disable allowing question marks.
  Flagged or question marked cells are not cleared.

- Clear surrounding cells (Ctrl + click):
  Surrounding cells are only cleared if the starting cell has been previously cleared and the number of surrounding flags is equal to the number of surrounding mines.

- Remove mine (Ctrl + Shift + click):
  Removes a mine from a cell, and clears the cell and other surrounding cells. Does nothing if cell is not mined.


Game timer
----------

Keeping track of time is done inside the Game class.
Time is displayed up to hundreds of a second.
The timer display is maxed at 59:59.99.

Game end
--------

The game ends when the player clears all the cells without mines (win), or clears a cell with a mine (lose).
When the game ends, all mines are shown. Mines that blow up are shown in red, and other mines in gray.


Number of mines
---------------

Display the number of mines left to discover. That is, the number of mines minus the number of flags.
This can be negative if there are more flags than mines.




Some things I want to do
------------------------

Basic game:
- Show mines when game is over
  - Show bad suspected mines (flags without mines)
  - Show exploded mine with diff graphics than other mines
- Double click to clear all surrounding cells
- Status bar
  - Show number of mines (minus flags?)
  - Max timer at 23:59:59.99 (unusual case)

Dev stuff:
- Cleanup code
  - Move action listener out of MineFieldPanel
- Add unit tests
- Switch to netbeans (?)
- Add build/run instructions
- Add documentation + how to play

Advanced:
- Add pathfinder game

Optional:
- Solver

Etc:
+ Add different sizes (small, medium, large)
  - Determine sizes to use...
