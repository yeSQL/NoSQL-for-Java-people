function (doc, meta) {
    if ('genusName' in doc) {
        emit(doc.genusName, meta.id);
    }
}
