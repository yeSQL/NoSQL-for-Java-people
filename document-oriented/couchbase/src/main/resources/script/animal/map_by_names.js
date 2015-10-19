function (doc, meta) {
    if ('speciesName' in doc && 'genusName' in doc) {
        emit([doc.speciesName, doc.genusName], meta.id);
    }
}
