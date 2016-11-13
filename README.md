Minesweeper
===========

Minesweeper is a simple game, which became very popular on Windows. I really liked playing it and wanted to implement my own version, just for fun. So here it is, Classic Minesweeper in Java.

You can learn more about the game online:

- https://en.wikipedia.org/wiki/Minesweeper_(video_game)
- https://en.wikipedia.org/wiki/Microsoft_Minesweeper
- http://www.minesweeper.info/wiki/Windows_Minesweeper

This is a work in progress. I'll finish it when I have a little bit of free time. For now, here are some screenshots:

![screenshot-1](/screenshots/20160823_001.png?raw=true "Screenshot 1")
![screenshot-2](/screenshots/20160825_001.png?raw=true "Screenshot 2")


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
  Removes a mine from a cell, and clears the cell and other surrounding cells without mines. Does nothing if cell is not mined.
  This makes the game easier, so by default is is disabled and there is an option to enable it.


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


Options
-------

- Allow question marks
- Allow removing mines (requires confirmation)
- Show hidden mines (requires confirmation)



Different types of games
------------------------

Appart from the classic game, there can be some other interesting games, like finding a path instead of removing all the mines.

- Classic:
  Goal is to clear all the unmined cells. Player loses by stepping on a mine.

- Clear mines:
  Goal is to remove all the mines. Player loses by failing to remove a mine.

- Pathfinder:
  Goal is to open a safe path from the left to the right border.

For all games there should always be a solution, and the first move should always be valid/safe.

Adding these probably won't require a lot of work. Probably creating a subclass of Game, and overriding the method to check if the game is over.



Some things I want to do
------------------------



Basic game:
OK - Mine graphics
- Show mines when game is over
  - Show bad suspected mines (flags without mines)
  OK - Show exploded mine with diff graphics than other mines
OK - Status bar
  - Show number of mines (minus flags?)
- Use mouse listener
- Confirmation for new game when selecting difficulty
- When a new game is started, don't recreate the whole UI!!!
* Clearing a lot of mines is slow! (really big board, setting the size manually)
  - reuse images and image icons


Dev stuff:
- Cleanup code
  - Move action listener out of MineFieldPanel
* Add good unit tests -> good coverage
- Switch to netbeans (?)
- Add build/run instructions
- Add documentation + how to play
  - Good readme and pics

Advanced:
- Add pathfinder game

Etc:
OK + Add different sizes (small, medium, large)
  - Determine sizes to use...
OK - Use Observer pattern for buttons (?)
- Unit tests -> Code coverage


Possible improvements:
- Better mine graphics
- Add help option with instructions
- Save player records
- Solver (?)

Notes:
- Images created with GIMP

