class A {
    a:Int;
    set_a(num:Int) :Object {
		a <- num
    };
};

class B inherits A{
    bark():Object{
		(new IO).out_int(a).out_string("\n")
	};
};

b.bark()



-- dispatch_classtag_
dispatch_1_bark:

B_bark:
	...



