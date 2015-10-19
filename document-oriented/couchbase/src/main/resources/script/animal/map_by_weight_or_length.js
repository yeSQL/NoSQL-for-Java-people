function (doc, meta) {
    if ('weight' in doc) {
        emit(doc.weight, meta.id);
    }
    if ('length' in doc) {
        emit(doc.length, meta.id);
    }
}