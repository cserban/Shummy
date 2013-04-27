Shummy
======

### Preprocessing

##### The proprocessing consists of 4 major steps:
1. First of all, all content from the corpus files are split into several sentences. A Sentence is seen as a list of Words, whereas a Word is a tuple of content, POS tag and a list of synonims. Further development will also entail the stem and lemma for the word. 
2. All words from these sentences are tagged using the Stanford Tagger. More details here: http://nlp.stanford.edu/software/tagger.shtml
3. After tagging words, it is safe to remove all punctuation and stopwords. There are various lists of stopwords. According to the provided results, only a list will be chosen.
4. Add synonims to each word in these sentences. In order to do that, the lexical database for English language WordNet is used. More details here: http://www.rednoise.org/rita/wordnet/documentation/


##### Example:
Let's take for example the first sentence:
  `The white-haired old man was sitting in his favorite chair, holding a thick book and rubbing his tired eyes.`

If using StanfordCoreNLP object, we could make use of the following features. 

1. split corpus docs in sentences and tokens
2. foreach token, we get:
    - word
    - POS
    - NE

We can also get the parse tree of the sentence:
```python

(ROOT (S (NP (DT The) (JJ white-haired) (JJ old) (NN man)) (VP (VBD was) (VP (VP (VBG sitting) (PP (IN in) (NP (PRP$ his) (JJ favorite) (NN chair)))) (, ,) (VP (VBG holding) (NP (DT a) (JJ thick) (NN book))) (CC and) (VP (VBG rubbing) (NP (PRP$ his) (JJ tired) (NNS eyes))))) (. .)))
```

... and the dependency graph of the sentence:
```python

-> sitting-VBG (root)
  -> man-NN (nsubj)
    -> The-DT (det)
    -> white-haired-JJ (amod)
    -> old-JJ (amod)
  -> was-VBD (aux)
  -> chair-NN (prep_in)
    -> his-PRP$ (poss)
    -> favorite-JJ (amod)
  -> holding-VBG (conj_and)
    -> man-NN (nsubj)
    -> book-NN (dobj)
      -> a-DT (det)
      -> thick-JJ (amod)
  -> rubbing-VBG (conj_and)
    -> man-NN (nsubj)
    -> eyes-NNS (dobj)
      -> his-PRP$ (poss)
      -> tired-JJ (amod)
```

... and the coreference link graph:
```python
{
  1=CHAIN1-["The white-haired old man" in sentence 1, "his" in sentence 1, "his" in sentence 1, "his" in sentence 2, "he" in sentence 2, "His" in sentence 3, "him" in sentence 6, "I" in sentence 8, "I" in sentence 9, "he" in sentence 9, "you" in sentence 14],
  2=CHAIN2-["his favorite chair" in sentence 1],
  5=CHAIN5-["his tired eyes" in sentence 1, "His eyes" in sentence 3, "my eyes" in sentence 36],
  9=CHAIN9-["his nineteen-year-old granddaughter , Valerie ," in sentence 2, "his nineteen-year-old granddaughter" in sentence 2, "Valerie" in sentence 2, "her" in sentence 3, "Valerie" in sentence 12, "I" in sentence 14, "I" in sentence 14, "Valerie" in sentence 15, "She" in sentence 16, "Valerie" in sentence 18, "I" in sentence 19, "Valerie 's" in sentence 23, "You" in sentence 23, "your" in sentence 24, "Valerie" in sentence 40, "Valerie" in sentence 45, "Valerie" in sentence 51, "I" in sentence 54, "Valerie" in sentence 56, "you" in sentence 57, "your" in sentence 57, "Valerie" in sentence 58, "her" in sentence 58, "Valerie" in sentence 61, "she" in sentence 61],
}
```

