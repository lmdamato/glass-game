# Glass game
Java project to solve the glass game (several glasses of various capacities, get to goal of one glass containing a certain goal quantity).

The initial situation is described as having a certain number of empty glasses of known capacity. The only allowed moves are:

* fill a glass
* empty a glass
* pour from a non-empty glass to another, non-full glass until the first glass gets empty or the second gets full (whichever first).

In other words, you cannot pour arbitrary quantities from or to any of the glasses. The game ends when one of the glasses contains *exactly* the desired target quantity. It does not end when the total quantity contained by all the glasses is equal to the target quantity, although getting from one to the other is quite straightforward (unless none of the glasses alone can contain the target quantity, but we're going to ignore that).
