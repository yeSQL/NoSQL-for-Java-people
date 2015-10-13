function (doc) {
    if ('genusName' in doc) {
        emit(doc.genusName, doc._id);
    }
}
