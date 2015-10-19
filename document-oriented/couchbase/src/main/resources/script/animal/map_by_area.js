function(doc, meta) {
    if ('areas' in doc) {
        for (var i in doc.areas) {
                emit(doc.areas[i], meta.id);
        }
    }
}