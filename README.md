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

- Toggle flag (Alt + click)

- Clear surrounding cells (Ctrl + click):
  Surrounding cells are only cleared if the starting cell has been previously cleared and the number of surrounding flags is equal to the number of surrounding mines.


Game timer
----------

Keeping track of time is done inside the Game class.
Time is displayed up to hundreds of a second.



Some things I want to do
------------------------

Basic game:
- Show mines when game is over
  - Show bad suspected mines (flags without mines)
  - Show exploded mine with diff graphics than other mines
- Add option to ? cells
- Clear cells when flagged or "?-ed"?
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
- Add option to remove/disarm mines
- Add pathfinder game

Optional:
- Solver

Etc:
+ Add different sizes (small, medium, large)
  - Determine sizes to use...
