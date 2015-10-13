function (doc) {
    if ('speciesName' in doc) { // guards data in unstructured world
        emit(doc.speciesName, doc);
    }
}
