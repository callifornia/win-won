def foldRight[A, B](as: List[A], acc: B, f: (A, B) => B): B =
    as match {
      case Nil => acc
      case head :: tail => f(head, foldRight(tail, acc, f)) }

def foldLeft[A,B](as: List[A], acc: B, f: (B,A) => B): B =
    as match {
      case Nil => acc
      case h :: tail => foldLeft(tail, f(acc,h), f)
    }


def append[A](a1: List[A], a2: List[A]): List[A] =
  foldRight[A, List[A]](a1, a2, (h, t) => h :: t)


def prepend[A](a1: List[A], a2: List[A]): List[A] =
  foldLeft[A, List[A]](a2, a1, (t, h) => h :: t)



//    1 :: 2 :: Nil   ||    x => List(x + 1)
def flatMap[A, B](as: List[A], f: A => List[B]): List[B] =
  foldRight[A, List[B]](
    as,
    Nil: List[B],
    (a, acc) => append(f(a), acc))


def flatMap[A, B](as: List[A], f: A => List[B]): List[B] =
  foldRight[A, List[B]](
    as,
    Nil: List[B],
    (a, acc) => append(acc, f(a)))


def flatMap[A, B](as: List[A], f: A => List[B]): List[B] =
  foldRight[A, List[B]](
    as,
    Nil: List[B],
    (a, acc) => prepend(f(a), acc))






