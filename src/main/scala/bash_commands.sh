#! /bin/bash

a2="hello there"
echo ${a2:l}
echo ${a2:u}
exit 1

Ермак не засланый вообще



#comparison
s1="a"
s2="b"
s3=$s1$s2
echo $s3 #ab
if [ $s1 == $s2 ]; then
	echo "string same"
else 
	echo "not same"
fi
		# compare lenght
if [ "$s1" \> "$s2" ]; then
	echo "string same"
else 
	echo "not same"
fi
exit 1

ls +a >>ok_not_ok.txt 2>&1
exit 1

ls -a 1>>ok.txt 2>>errors_are_goint_to_be_saved_here.txt
exit 1



while read line
do
	echo "Line from file: $line"
done < $1
exit 1



#files
# ./bash_commands.sh bmw audi reno
echo $@ #unlimited numbers of inputs
exit 1

echo $1 $2 $3
exit 1



#for in
for i in {1..10}
do
	if [[ $i == 2 ]]; then
		continue
	fi
	echo "current is: $i"  # with a continue this will not be printed for $i=2
	if [[ $i == 5 ]]; then
		echo "going to breake"
		break
	fi
done
exit 1



#for in
for i in 1 2 3 4 5
do
	echo "current is: $i"
done
exit 1



#until loop. Will run until condition is false
number=12
until [[ $number -le 0 ]]
do
	echo "The number is: $number"
	number=$(( number-1 ))
done
exit 1



#while loop. Will run until condition is true
number=12
while [[ $number -gt 0 ]]
do
	echo "The number is: $number"
	number=$(( number-1 ))
done
exit 1



#case statement
car="audias"
case $car in
	"bmw")
		echo "The car is bmw" ;;
	"audi")
		echo "The car is audi" ;;
	*)
		echo "Some unknown car" ;;
esac	
exit 1



# if condition.
# for String should be used with a `==`, integers is ok with `-eq, -gt, -lt`
name="1hello"
another_name="another_name"
age=18
if [[ $name == "hello" && $age -eq 10 || $another_name == "foo" ]]; then
	echo "true condition"
elif [[ $name == "1hello" && $age -lt 10 || $another_name == "foo2" ]]; then
	echo "another else condition"
elif [[ $another_name == "another_name" ]]; then
	echo "another_name condition"
else
	echo "false condition"
fi
exit 1



#read from console and write into the file
cat > file.txt
exit 1
: 'multiline comment. read from console and append into the file'
cat >> file.txt
exit 1

