# Conway's Game of Life

Simple zero-player game / automaton. Hoping to use this as a way to explore new
languages without the hassle of project planning and without aimless meandering.

[Wikipedia](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)

Input: either `M` x `N` dimensions to randomly generate with given `density`, or
.txt file whose first line is `M N` values and the remaining lines are the system
seed, where `.` is a dead cell and `X` is a live cell.


## Constraints

### Formatting

Program must both accept as input and generate as output the format found in
`./resources/seeds`.


#### Grid specifications:

  - dead cells are marked with `.`
  - live cells are marked with `X`
  - cells in a row are separated with two spaces
  - rows are separated by a newline character and no others (incl. whitespace)

    ```
    .  .  .  .
    .  X  X  .
    .  X  X  .
    .  .  .  .
    ```


#### Input file specifications:

  - the first line must be `M N` values, separated by a single space and
  followed by a single newline
  - the remaining file content will be entirely grid format, as specified above

    ```
    4 4
    .  .  .  .
    .  X  X  .
    .  X  X  .
    .  .  .  .
    ```


#### Terminal output specifications:

  - Each iteration has a preceding and a following "bookend" consisting of
  repeated `=` to match length of the grid rows.
  - the grid of the current iteration as specified above
  - on each iteration, clear previous, draw current, sleep 500ms

    ```
    ==========
    .  .  .  .
    .  X  X  .
    .  X  X  .
    .  .  .  .
    ==========
    ```
