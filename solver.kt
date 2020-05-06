import java.lang.NumberFormatException
import kotlin.reflect.typeOf
import kotlin.UByte.Companion
import kotlin.collections.MutableList
import kotlin.math.*
import kotlin.random.Random

class Sudokuboard() {
    var rows: List<MutableList<Int>> = listOf(mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf())

    fun build_board() {
        for (i in 0..8) {
            var x = readLine().toString().trim()
            for (j in x) {
                if (j != ' ')
                    this.rows[i].add(j.toInt() - 48)
            }
        }
    }

    fun column_check(col: Int): Boolean {
        var temp: MutableList<Int> = mutableListOf()
        var result: Boolean = false
        for (j in 0..8)
            temp.add(this.rows[j][col - 1])
        result = repeating(temp)

        return result
    }

    fun row_check(row: Int): Boolean {
        var temp: MutableList<Int> = mutableListOf()
        var result: Boolean = false
        for (j in 0..8)
            temp.add(this.rows[row - 1][j])
        result = repeating(temp)

        return result
    }

    fun print_board() {
        var pos_r = 0
        var pos_c = 0
        for (i in this.rows) {
            pos_c++
            for (j in i) {
                pos_r++
                print(" $j ")
                if (pos_r % 3 == 0) print("  ")
            }
            pos_r = 0
            println("")
            if (pos_c % 3 == 0) println("")
        }
    }

    fun get_at(x: Int, y: Int): Int {
        return this.rows[x][y]
    }

    fun quad_check(): Boolean {
        var result: Boolean = false
        for (i in 0..8) {
            var a = 3 * (i / 3)
            var b = 3 * (i % 3)
            var c = a + 2
            var d = b + 2
            var temp: MutableList<Int> = mutableListOf()
            for (j in a..c) {
                for (k in b..d)
                    temp.add(this.rows[j][k])
            }
            result = repeating(temp)
            if (!result) return false
        }
        return result
    }

    fun check_rows(): Boolean {
        var temp = false
        for (i in 1..9) {
            temp = this.row_check(i)
            if (!temp)
                return false
        }
        return temp
    }

    fun check_cols(): Boolean {
        var temp = false
        for (i in 1..9) {
            temp = this.column_check(i)
            if (!temp)
                return false
        }
        return temp
    }

    fun canbe_placed(x: Int, y: Int, input: Int): Boolean {
        var prev = this.rows[x][y]
        this.rows[x][y] = input
        var result = this.check_cols() && this.check_rows() && this.quad_check()
        this.rows[x][y] = prev
        return result
    }

    fun unsolved(): String {
        var y = ""
        for (i in 0..8) {
            for (j in 0..8)
                if (this.rows[i][j] == 0) {
                    y = y + i + j
                }
        }
        return y
    }

    fun solve_board(x: String): Boolean {

        if (x.isEmpty())
            return true

        for (i in 1..9) {
            var temp = 0

            if (!this.canbe_placed(x[0].toInt() - 48, x[1].toInt() - 48, i))
                continue

            this.rows[x[0].toInt() - 48][x[1].toInt() - 48] = i

            if (this.solve_board(this.unsolved()))
                return true

            this.rows[x[0].toInt() - 48][x[1].toInt() - 48] = 0
        }

        return false
    }

}


fun repeating(x:MutableList<Int>):Boolean{
     for(i in x){
            var count=0
            for(j in x){
                if(i==j && i!=0)
                    count++
                if(count>1)
                    return false

            }
        }
    return true
}
fun change(x:String):String{
    var y=""
    for(i in 1..x.length-1)
        y+=x[i]

    return y
}

fun main(){
       var s=Sudokuboard()
       s.build_board()

       if(s.solve_board(s.unsolved())) {
           s.print_board()
           println("\nSuccessfully solved !!")
       }
        else
           println("\nInvalid Puzzle !!")

}
