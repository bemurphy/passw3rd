require 'base64'
require 'openssl'
require 'optparse'

module Passw3rd
  class PasswordClient 
    #  def self.run argv = ARGV
    password_file = nil
    output_path = nil
    gen_key_path = nil
    key_path = nil

    opts = OptionParser.new
    opts.banner = 'Usage: password_client [options]'
    opts.on('-d', '--decrypt PATH_TO_PASSWORD', 'Path to password file') do |opt|
      password_file = opt
    end
    opts.on('-e', '--encrypt OUTPUT_PATH', 'Write the password to this location') do |opt|
      output_path = opt
    end
    opts.on('-p', '--key-path KEY_PATH', 'Use the keys specificed in this directory for encryption or decryption') do |opt|
      key_path = opt
      if !File.directory?(File.expand_path(key_path))
        raise "#{opt} must be a directory"
      end
    end
    opts.on('-k', '--generate-key [PATH]', 'generate key/iv and store in PATH, defaults to the home directory') do |opt|
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

    # default the key directory to the users home, can also be specified by -p [path]
    if key_path.nil?
      key_path = ENV["HOME"]
    end

    # decrypt password_file using the key/IV in key_path
    if password_file
      decrypted = Passw3rd::PasswordService.getPassword(password_file, key_path)

      puts("The password is: #{decrypted}")
    end

    # encrypt password, store it in output path
    if output_path
      begin
        system 'stty -echo'
        print "Enter the password: "
        password = STDIN.gets.chomp
      ensure
        system 'stty echo; echo ""'
      end

      Passw3rd::PasswordService.write_password_file(password, output_path, key_path)
      puts "Wrote password to #{output_path}"
    end
  end
end