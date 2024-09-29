val list = 7 :: 6 :: 5 :: 4 :: 10 :: 3 :: 13 :: 2 :: 1 :: 0 :: Nil

def merge(listOne: List[Int], listTwo: List[Int]): List[Int] = (listOne, listTwo) match {
  case (Nil, list) => list
  case (list, Nil) => list
  case (x :: xs, y :: ys) =>
    if(x < y) x :: merge(xs, listTwo)
    else y :: merge(listOne, ys)
}

def mergeSort(list: List[Int]): List[Int] = list match {
  case Nil => list
  case xs :: Nil => List(xs)
  case _ =>
    val (left, right) = list splitAt list.length / 2
    merge(mergeSort(left), mergeSort(right))
}

mergeSort(list)