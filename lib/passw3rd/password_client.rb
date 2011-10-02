require 'base64'
require 'openssl'
require 'optparse'

module Passw3rd
  class PasswordClient 
    password_file = nil
    gen_key_path = nil
    key_path = nil
    password_dir = nil
    mode = nil

    opts = OptionParser.new
    opts.banner = 'Usage: password_client [options]'

    opts.on('-d', '--decrypt PATH_TO_PASSWORD', 'Path to password file') do |opt|
      password_file = opt
      mode = "decrypt"
    end

    opts.on('-e', '--encrypt PASSWORD_FILE', 'Write the password to this location') do |opt|
      password_file = opt
      mode = "encrypt"
    end

    opts.on('-k', '--key-dir KEY_PATH', 'Use the keys specificed in this directory for encryption or decryption (default is ~/)') do |opt|
      key_path = opt
      if !File.directory?(File.expand_path(key_path))
        raise "#{opt} must be a directory"
      end
    end

    opts.on('-p', '--password-dir PATH', 'Read and write password files to this directory (default is ~/)') do |opt|
      password_dir = opt
      Passw3rd::PasswordService.password_file_dir = password_dir
      if !File.directory?(File.expand_path(password_dir))
        raise "#{password_dir} must be a directory"
      end      
    end

    opts.on('-g', '--generate-key [PATH]', 'generate key/iv and store in PATH, defaults to the home directory') do |opt|
      gen_key_path = opt
      if gen_key_path.nil?
        gen_key_path = ENV["HOME"]
      end
      if !File.directory?(File.expand_path(gen_key_path))
        raise "#{opt} is not a directory"
      end
    end

    opts.on_tail("-h", "--help", "Show this message") do
      puts opts
      exit
    end

    opts.parse(ARGV)

    # generate key/IV
    if gen_key_path
      Passw3rd::KeyLoader.create_key_iv_file(gen_key_path)
      puts "generated keys in #{gen_key_path}"
    end

    # decrypt password_file using the key/IV in key_path
    if mode == "decrypt"
      decrypted = Passw3rd::PasswordService.get_password(password_file, key_path)
      puts "The password is: #{decrypted}"
    end

    # encrypt password, store it in output path
    if mode == "encrypt"
      begin
        system 'stty -echo'
        print "Enter the password: "
        password = STDIN.gets.chomp
      ensure
        system 'stty echo; echo ""'
      end

      file = Passw3rd::PasswordService.write_password_file(password, password_file, key_path)
      puts "Wrote password to #{file}"
    end
  end
end