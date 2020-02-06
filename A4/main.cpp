//CPS311 F2016 A4 Group #18 BARSS Nicholas, HUNT Spencer, MESSINA Stephen

#include <iostream>
#include <stdlib.h>
using namespace std;

class vehicle{
protected:
	char * make;
	int year;
	double base_cost;
	double price;
public:
	vehicle(char * v_mk, int v_yr, double v_b_cost);
	~vehicle(void){
				delete [] make;
				printf("Group #18\n");
			}
	virtual void set_price(double v_markup) = 0;
	char * get_make(){ return make; }
	int get_year(){ return year;}
	double get_price(){ return price;}
};
vehicle::vehicle(char * v_mk, int v_yr, double v_b_cost):  year(v_yr) {
	make = new char[25];
	strcpy(make, v_mk);
	base_cost = v_b_cost;
}

class car:public vehicle{
public:
	car(double c_b_cst, int c_yr = 2016, char * c_mk = "Corolla"):vehicle(c_mk, c_yr, c_b_cst){}
	void set_price(double v_markup){
		price = v_markup + base_cost;
	}
};

ostream& operator<<(ostream& os, car& c1){
	os << "For a car make " << c1.get_make() << ", " << c1.get_year() << ", the price is $"
			<< c1.get_price() << " from Group 18" << endl;
	return os;
}


class truck:public vehicle{
private:
	double recycle_factor;
public:
	truck(int t_yr, char * t_mk = "Tacoma", double t_rcy_ftr = 1.04, double t_b_cost = 65000):
		vehicle(t_mk, t_yr, t_b_cost){
		recycle_factor = t_rcy_ftr;
	}
	void set_price(double t_markup){
		price = recycle_factor *(t_markup + base_cost);
	}
	double get_recycle_factor(){
		return recycle_factor;
	}
	friend ostream& operator<<(ostream& os, truck& t1){ //This doesn't work yet ask paneer
		os << "For a truck make " << t1.make << ", " << t1.year << ", with recycle factor "
				<< t1.recycle_factor*100 - 100 << "% the price is $"  << t1.price << endl;
		return os;
	}
};

int main(){
	vehicle * v_ptr;
	static car c1(27000, 2015);
	static truck t1(2014,"Tacoma", 1.09);
	static truck t2(2016, "Highlander", 1.04, 50000);
	cout.setf(ios::fixed);
	cout.precision(2);

	v_ptr = &c1;
	v_ptr->set_price(3000);
	cout << c1;

	v_ptr = &t1;
	v_ptr->set_price(4500);
	cout << t1;
	v_ptr = &t2;
	v_ptr->set_price(3500);
	cout << t2;

}









