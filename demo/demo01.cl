class Dog {
    age:Int <- 10;
    height:Int <- age*2;
};

class Main inherits IO{
    d:Dog <- new Dog;	
    age:Int <- 10;
    height:Int <- age*2;
    
    main():Object {
	{
	    self.out_string("hello world!\n");
	    self.out_int(age);
	    out_string("\n");
	    out_int(height);
	}
    };
};
