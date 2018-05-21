i=0
while [ $i -lt 28 ]; do
    make A=$i
    i=$[i + 1]
done
