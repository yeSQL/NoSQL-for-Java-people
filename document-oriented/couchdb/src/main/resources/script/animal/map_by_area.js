function(doc) {
    for (var i in doc.areas) {
        emit(doc.areas[i], doc._id);
    }
}