Shummy
======

### Preprocessing

#### The proprocessing consists of 4 major steps:
1. First of all, all content from the corpus files are split into several sentences.
2. All words from these sentences are tagged using the Stanford Tagger. More details here: http://nlp.stanford.edu/software/tagger.shtml
3. After tagging words, it is safe to remove all punctuation and stopwords. There are various lists of stopwords. When the project will extend, the list with the best results will be kept.
4. Add synonims to each word in these sentences. In order to do that, the lexical database for English language WordNet is used. More details here: http://www.rednoise.org/rita/wordnet/documentation/

