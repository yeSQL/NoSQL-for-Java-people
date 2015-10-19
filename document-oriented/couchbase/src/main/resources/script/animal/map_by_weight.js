function (doc, meta) {
    if ('weight' in doc) {
        emit(doc.weight, meta.id);
    }
}