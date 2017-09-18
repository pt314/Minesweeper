Minesweeper
===========

Minesweeper is a simple game, which became very popular on Windows. I really liked playing it a long time ago and wanted to implement my own version, just for fun. So here it is, Classic Minesweeper in Java.

You can learn more about the game online:

- https://en.wikipedia.org/wiki/Minesweeper_(video_game)
- https://en.wikipedia.org/wiki/Microsoft_Minesweeper
- http://www.minesweeper.info/wiki/Windows_Minesweeper

This was just a small exercise and was not completely finished (code has some TODOs), but the game is complete and playable, including some extra options. Here are some screenshots:

![screenshot-1](/screenshots/20160823_001.png?raw=true "Screenshot 1")
![screenshot-2](/screenshots/20160825_001.png?raw=true "Screenshot 2")


Difficulty levels
-----------------

- Beginner (8x8, 10 mines)
- Intermediate (16x16, 40 mines)
- Expert (24x24, 99 mines)

Game options
------------

- Allow question marks
- Allow removing mines (requires confirmation)
- Show hidden mines (requires confirmation)

Game start and end
------------------

The game and timer start when the player clears the first cell. To prevent the player from losing on the first move, mines are placed after the first cell is cleared. To place the mines, a sample with uniform distribution is selected from all the cells excluding the first cleared cell.

The game ends when the player clears all the cells without mines (win), or clears a cell with a mine (lose).
When the game ends, all mines are shown. Mines that blow up are shown in red, and other mines in gray.

User interface
--------------

A timer starts when the first cell is cleared and stops when the game ends. Time is displayed up to hundreds of a second and the timer display is maxed at 59:59.99.

The number of mines left to discover is shown on the top left. That is, the number of mines minus the number of flags. This can be negative if there are more flags than mines.

The number of flags placed is also shown on the top right.


Player actions
--------------

- Clear cell (click): Be careful...

- Toggle mark (Alt + click): Cycle between no mark, flag, and question mark. Flags may be used for cells where the player believes there is a mine. Question marks may be used for cells where the player believes there could be a mine, but is not sure if there is. Note that allowing question marks makes it slower to remove a flag, so there is a menu option to enable or disable allowing question marks. Flagged or question marked cells are not cleared.

- Clear surrounding cells (Ctrl + click): Clears all non-flagged surrounding cells. Surrounding cells are only cleared if the clicked cell has been previously cleared and the number of surrounding flags is equal to the number of surrounding mines.

- Remove mine (Ctrl + Shift + click): Removes a mine from a cell, and clears the cell and other surrounding cells without mines. Does nothing if cell is not mined. This cheat makes the game easier and boring, so by default it is disabled and there is an option to enable it (mostly used for debugging earlier on).


Different types of games
------------------------

Apart from the classic game, there can be some other interesting games, like finding a path instead of removing all the mines.

- Classic:
  Goal is to clear all the unmined cells. Player loses by stepping on a mine.

- Clear mines:
  Goal is to remove all the mines. Player loses by stepping on a mine.

- Pathfinder:
  Goal is to open a safe path from the left to the right border, or from a starting cell to a destination cell.

For all games there should always be a solution, and the first move should always be valid/safe.

Never did this, but adding these wouldn't require a lot of work. Probably creating a subclass of `Game`, and overriding the method to check if the game is over.


About the code
--------------

Some brief comments:

- The main class is `Minesweeper` - run this to start the game (if images don't show, check to make sure images are in the right folder `res/img`).
- Classes related to actual game state are in the `game` package, and UI classes are in the `gui` package.
- The `Game` class keeps the state of the game and has methods for clearing and flagging cells, etc., and the `MineFieldGenerator` has methods for adding random mines.
- The observer pattern is used to update the UI: each `Cell` is an `Observable` and has a corresponding `MineFieldButton` in the UI, registered as an `Observer`.


Possible improvements
---------------------

User interface:
- When selecting new game option, ask for confirmation if a game is in progress.
- When the game is over, show bad suspected mines (flags without mines).
- Clearing a lot of cells is slow on a really big board (not for the available sizes though).
  - Improve drawing speed, reuse images and image icons (may not be a problem with the board sizes used)
- Better mine graphics.
- Add help option with instructions.

Game functionality:
- Save player records.

Dev stuff:
- Cleanup code and TODOs.
- Add good unit tests.
- Add build/run instructions.
- Distribute as jar (how to distribute as a jar and have the images work?).

Advanced:
- Hints and solver (of course this makes the game boring, but just for fun as a coding exercise).
- Add other game types, like the pathfinder game (the Windows 8/10 version of Minesweeper has a similar more advanced "Adventure" mode).


Notes
-----

- Images created with [GIMP](https://www.gimp.org/)
