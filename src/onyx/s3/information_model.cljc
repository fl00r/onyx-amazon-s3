(ns onyx.s3.information-model)

(def model
  {:catalog-entry
   {:onyx.plugin.s3-input/input
    {:summary "An input task to read objects from an s3 bucket."
     :model {:s3/deserializer-fn 
             {:doc "A namespaced keyword pointing to a fully qualified function that will deserialize from bytes to segments. Currently only reading from newline separated values is supported, thus the serializer must deserialize line by line."
              :type :keyword}

             :s3/access-key
             {:doc "The Amazon S3 credentials key."
              :type :string}

             :s3/secret-key
             {:doc "The Amazon S3 credentials secret."
              :type :string}

             :s3/region 
             {:doc "The S3 region endpoint to use."
              :optional? true
              :type :string}

             :s3/bucket 
             {:doc "The name of the s3 bucket to read objects from."
              :type :string}

             :s3/buffer-size-bytes
             {:doc "Size of the buffer to read the S3 object into."
              :default 10000000
              :type :integer}

             :s3/prefix 
             {:doc "Filter the keys to be read by a supplied prefix."
              :type :string}

             :s3/file-key
             {:doc "When set, includes the S3 key of file from which the segment's line was read under this key."
              :type :string
              :optional? true}}}

    :onyx.plugin.s3-output/output
    {:summary "An output task to write objects to an S3 bucket."
     :model {:s3/bucket 
             {:doc "The name of the s3 bucket to write to."
              :type :string}

             :s3/access-key
             {:doc "The Amazon S3 credentials key."
              :type :string}

             :s3/secret-key
             {:doc "The Amazon S3 credentials secret."
              :type :string}

             :s3/prefix 
             {:doc "A prefix to prepend to the keys generated by `:s3/key-naming-fn`."
              :optional? true
              :type :string}

             :s3/serializer-fn 
             {:doc "A namespaced keyword pointing to a fully qualified function that will serialize the segments to bytes."
              :type :keyword}

             :s3/key-naming-fn 
             {:doc "A namespaced keyword pointing to a fully qualified function that be supplied with the Onyx event map, and produce an s3 key for the batch."
              :type :keyword}


             :s3/serialize-per-element? 
             {:doc "Flag for whether to serialize as an entire batch, or serialize per element and separate by newline characters."
              :optional? true
              :type :boolean}

             :s3/region 
             {:doc "The S3 region endpoint to use (bucket will still determine the location of your data)."
              :optional? true
              :type :string}

             :s3/content-type 
             {:doc "Optional content type for S3 object."
              :optional? true
              :type :string}

             :s3/encryption 
             {:doc "Optional server side encryption setting."
              :choices [:none :aes256]
              :optional? true
              :type :keyword}}}}

   :lifecycle-entry
   {:onyx.plugin.s3-input/input
    {:model
     [{:task.lifecycle/name :input-lifecycles
       :lifecycle/calls :onyx.plugin.s3-input/s3-input-calls}]}

    :onyx.plugin.s3-output/output
    {:model
     [{:task.lifecycle/name :output-lifecycles
       :lifecycle/calls :onyx.plugin.s3-output/s3-output-calls}]}}

   :display-order
   {:onyx.plugin.s3-input/input
    [:s3/deserializer-fn :s3/bucket :s3/prefix :s3/force-content-encoding]

    :onyx.plugin.s3-output/output
    [:s3/serializer-fn :s3/prefix :s3/region :s3/encryption :s3/content-type :s3/serialize-per-element? :s3/key-naming-fn :s3/bucket]}})
