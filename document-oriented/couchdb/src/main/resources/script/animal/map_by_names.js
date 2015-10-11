function (doc) {
    emit([doc.speciesName, doc.genusName], doc._id);
}
