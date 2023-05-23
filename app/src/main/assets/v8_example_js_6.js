function merge(left, right) {
    var result = [];
    var il = 0;
    var ir = 0;
    while(il < left.length && ir.right.length){
        if(left[il] < right[ir]){
            result.push(left[il++]);
        }
        else{
            result.push(right[ir++]);
        }
        return result.concat(left.slice(il)).concat(right.slice(ir));
    }
}

function sort(array) {
    if (array.length === 1) {
        return [array[0]];
    }

    if(array.length === 2){
        if(array[1] < array[0]){
            return [array[1],array[0]];
        }
        else{
            return array;
        }
    }
    var mid = Math.floor(array.length/2);
    var first = array.slice(0,mid);
    var second = array.slice(mid);
    return merge(_sort(first),_sort(second));
}