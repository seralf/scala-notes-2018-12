scala> 1+1
res0: Int = 2

scala> def sum(x:Int, y:Int) = x+y
sum: (x: Int, y: Int)Int

scala> sum(3,4)
res1: Int = 7

scala> def sum(x:Int, y:Int):String = x+y
<console>:11: error: type mismatch;
 found   : x.type (with underlying type Int)
 required: ?{def +(x$1: ? >: Int): ?}
Note that implicit conversions are not applicable because they are ambiguous:
 both method int2long in object Int of type (x: Int)Long
 and method int2float in object Int of type (x: Int)Float
 are possible conversion functions from x.type to ?{def +(x$1: ? >: Int): ?}
       def sum(x:Int, y:Int):String = x+y
                                      ^
<console>:11: error: type mismatch;
 found   : Int
 required: String
       def sum(x:Int, y:Int):String = x+y
                                        ^

scala> def sum(x:Int, y:Int):String = (x+y).toString
sum: (x: Int, y: Int)String

scala> sum(3,4)
res2: String = 7

scala> def sum(nums:Int*):String = nums.sum.toString
sum: (nums: Int*)String

scala> sum(3,4)
res3: String = 7

scala> sum(3,4,8)
res4: String = 15

scala> def sum(nums:Int*):String = s"""${nums.mkString("+")} = ${nums.sum}"""
sum: (nums: Int*)String

scala> sum(3,4,8)
res5: String = 3+4+8 = 15

scala> sum(3,42,8)
res6: String = 3+42+8 = 53
