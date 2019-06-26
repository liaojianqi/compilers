class C {
	a : Int;
	b : Bool;
	init(x : Int, y : Bool) : C {
           {
		a <- x;
		b <- y;
		self;
           }
	};
};

Class Main {
	main():C {
	  (new C).init(1,true)
	};
};

class A {};
class B {};
class D {};
class E inherits A {};
class F inherits A {};
class G inherits B {};
class H inherits G {};
class I inherits H {};
