# import required module
import os

# assign directory
directory = os.getcwd()

# iterate over files in
# that directory
for filename in os.listdir(directory):
  f = os.path.join(directory, filename)
  # checking if it is a file
  if os.path.isfile(f):

    just_the_file = f.split('\\')[-1]

    if len(just_the_file.split('_')) > 1:
      movie = just_the_file.split('_')[0]
      clipname = just_the_file.split('_')[1].split('.mp3')[0]

      if clipname == 'wavs.py':
        continue

      # print the first part of Java code
      # the declared static variables
      # print('''public static final String {}_{} = "{} {}";'''.format(movie, clipname, movie[0], clipname))
      part1_text = '''\npublic static final String {}_{} = "{} {}";'''.format(movie, clipname, movie[0], clipname)

      with open("part1_wavs.txt", "a") as myfile:
        myfile.write(part1_text)

# this for loop prints out the 2nd part of the Java code
# the additional calls to the functions for each clip
for filename in os.listdir(directory):
  f = os.path.join(directory, filename)
  # checking if it is a file
  if os.path.isfile(f):
    # print(f)

    just_the_file = f.split('\\')[-1]

    if len(just_the_file.split('_')) > 1:
      movie = just_the_file.split('_')[0]
      clipname = just_the_file.split('_')[1].split('.mp3')[0]

      if clipname == 'wavs.py':
        continue
      if clipname == 'wavs.txt':
        continue

      part2_text = '''
        String {}Path = soundboardData.get({}_{});
        if ({}Path == null || !new File({}Path).exists()) {{
            create{}SampleTempFile();
        }}'''.format(clipname, movie, clipname, clipname, clipname, clipname)

      with open("part2_wavs.txt", "a") as myfile:
        myfile.write(part2_text)

# this last for loop prints out the
# functions for each .wav file

# copy and paste these 3 and add to
# MainActivity.class file
for filename in os.listdir(directory):
  f = os.path.join(directory, filename)
  # checking if it is a file
  if os.path.isfile(f):
    # print(f)

    just_the_file = f.split('\\')[-1]

    if len(just_the_file.split('_')) > 1:
      movie = just_the_file.split('_')[0]
      clipname = just_the_file.split('_')[1].split('.mp3')[0]

      if clipname == 'wavs.py':
        continue

      if clipname == 'wavs.txt':
        continue
      # print(movie + " is the movie and the clip is " + clipname)

    part3_text = '''
    private void create{}SampleTempFile() {{
        try {{
            File file = File.createTempFile("{}_{}", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {{
                InputStream in = getResources().openRawResource(R.raw.{}_{});
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put({}_{}, file.getPath());
                saveSoundboardData();
            }} catch (IOException e) {{
                Log.w(TAG, "Unable to write tmp file!", e);
            }}
        }} catch (IOException e) {{
            Log.w(TAG, "Unable to create tmp file!", e);
        }}
    }}'''.format(clipname, movie, clipname, movie, clipname, movie, clipname)

    with open("part3_wavs.txt", "a") as myfile:
        myfile.write(part3_text)
