function (doc) {
    if ('weight' in doc) {
        emit(doc.weight, doc._id);
    }
    if ('length' in doc) {
        emit(doc.length, doc._id);
    }
}