function (doc, meta) {
    if ('speciesName' in doc) { // guards data in unstructured world
        emit(doc.speciesName, meta.id);
    }
}
