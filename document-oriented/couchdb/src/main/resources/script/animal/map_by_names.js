function (doc) {
    if ('speciesName' in doc && 'genusName' in doc) {
        emit([doc.speciesName, doc.genusName], doc._id);
    }
}
