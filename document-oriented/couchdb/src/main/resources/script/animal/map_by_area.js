function(doc) {
    if ('areas' in doc) {
        for (var i in doc.areas) {
                emit(doc.areas[i], doc._id);
        }
    }
}