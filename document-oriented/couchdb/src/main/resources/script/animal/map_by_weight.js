function (doc) {
    if ('weight' in doc) {
        emit(doc.weight, doc._id);
    }
}