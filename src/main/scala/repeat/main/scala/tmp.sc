val cents = 1 :: 5 :: 10 :: Nil
val number = 43


def function(number: Int, cents: Set[Int], result: List[Int] = Nil): List[Int] =
  number match {
    case 0 => result
    case _ =>
      number - cents.max match {
        case 0            => result :+ cents.max
        case n if n < 0   => function(number, cents.-(cents.max), result)
        case n if n > 0   => function(number - cents.max, cents, result :+ cents.max)
      }
  }

function(number, cents.toSet)







/*
* Find the maximum value in an Array
*
* */
val array: Array[Int] = Array(1,3,4,2,6,9,7,8,12)
def findMax(array: Array[Int]): Int =
  if (array.isEmpty) 0
  else array.foldLeft(array(0)){ (acc, el) =>
    if (acc < el) el
    else acc
  }

findMax(array)