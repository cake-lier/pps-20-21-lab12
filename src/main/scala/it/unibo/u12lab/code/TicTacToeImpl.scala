package it.unibo.u12lab.code

import alice.tuprolog.Theory
import it.unibo.u12lab.code.Scala2P._
import alice.tuprolog.{Int => PrologInt}

class TicTacToeImpl(fileName: String) extends TicTacToe {
  private val engine = mkPrologEngine(new Theory(ClassLoader.getSystemResourceAsStream(fileName)))
  private val size = 3

  createBoard()

  override def createBoard(): Unit = {
    val goal = "retractall(board(_)),newboard(B),assert(board(B))"
    solveWithSuccess(engine, goal)
  }

  override def checkCompleted(): Boolean = {
    val goal = "board(B),boardfilled(B)"
    solveWithSuccess(engine, goal)
  }

  override def checkVictory(): Boolean = {
    val goal = "board(B),threeinarow(B)"
    solveWithSuccess(engine, goal)
  }

  override def isAFreeCell(i: Int, j: Int): Boolean = {
    val goal = s"board(B),not(filledsquare(B,${i * size + j + 1}))"
    solveWithSuccess(engine, goal)
  }

  private def setCell(pos: Int, player: String): Unit = {
    val goal = s"retract(board(B)),!,setsquare(B,$pos,$player,B2),assert(board(B2))"
    solveWithSuccess(engine, goal)
  }

  override def setHumanCell(i: Int, j: Int): Unit = {
    val humanCellSymbol = "'X'"
    setCell(i * size + j + 1, humanCellSymbol)
  }

  override def setComputerCell(): Array[Int] = {
    val variable = "X"
    val goal = s"board(B),response(B, 'O', $variable)"
    val pos = solveOneAndGetTerm(engine, goal, variable).asInstanceOf[PrologInt].intValue()
    val computerPlayerSymbol = "'O'"
    setCell(pos, computerPlayerSymbol)
    Array((pos - 1) / size, (pos - 1) % size)
  }

  override def toString: String = {
    val variable = "B"
    val goal = s"board($variable)"
    solveOneAndGetTerm(engine, goal, variable).toString
  }
}
